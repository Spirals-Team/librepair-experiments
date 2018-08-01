#include "caf/all.hpp"
#include "caf/io/all.hpp"
#include "./Worker.hpp"
using namespace std;
using namespace caf;
using namespace rapidjson;

namespace Texera
{


	struct agent_state
	{
		string json_str;
		Document json_doc;
		unordered_set<Worker> workers;
		unordered_set<Worker> idle_workers;
		unordered_map<int, ifstream> inputs;
		unordered_map<int, vector<vector<string>>> pending_res;
		unordered_map<int, int> still_working;
		bool flush;
		int batch_size;
	};



	//code from:
	//https://stackoverflow.com/questions/236129/the-most-elegant-way-to-iterate-the-words-of-a-string?noredirect=1&lq=1

	template<typename Out>
	void split(const std::string &s, char delim, Out result) {
		std::stringstream ss(s);
		std::string item;
		while (std::getline(ss, item, delim)) {
			*(result++) = item;
		}
	}

	std::vector<std::string> split(const std::string &s, char delim) {
		std::vector<std::string> elems;
		split(s, delim, std::back_inserter(elems));
		return elems;
	}



	void Parse(Agent::stateful_pointer<agent_state> self,const string& task)
	{
		self->state.json_str = task;
		self->state.json_doc.Parse(task.c_str());
		self->state.flush = false;
		const Document& d = self->state.json_doc;
		assert(d["queue"].IsArray());
		const Value& q = d["queue"];
		for (SizeType i = 0; i < q.Size(); i++)
		{
			if (!q[i].HasMember("from"))
				self->state.inputs[i] = ifstream(q[i]["filename"].GetString());
		}
	}

	pair<int,vector<vector<string>>> SelectWork(Agent::stateful_pointer<agent_state> self)
	{

		Value& q=self->state.json_doc["queue"];
		auto& inputs = self->state.inputs;
		auto& pending_res = self->state.pending_res;
		auto& still_working = self->state.still_working;
		auto batch_size = self->state.batch_size;
		Document::AllocatorType& allocator = self->state.json_doc.GetAllocator();
		int c = 0;
		for (Value& v : q.GetArray())
		{
			if (v.HasMember("from"))
			{
				auto arr = v["from"].GetArray();
				auto id = v["id"].GetInt();
				auto cond1 = (v.HasMember("block") && v["block"].GetBool() && all_of(arr.Begin(), arr.End(), [&](const Value& i)
				{
					for (auto j : still_working)
						if (j.first < id && j.second)
							return false;
					int idx = i.GetInt();
					return (pending_res.find(idx) != pending_res.end() && !pending_res[idx].empty())
						|| (inputs.find(idx) != inputs.end() && inputs[idx].good());
				}));
				auto cond2 = ((!v.HasMember("block") || !v["block"].GetBool()) && all_of(arr.Begin(), arr.End(), [&](const Value& i)
				{
					int idx = i.GetInt();
					return (pending_res.find(idx) != pending_res.end() && !pending_res[idx].empty())
						|| (inputs.find(idx) != inputs.end() && inputs[idx].good());
				}));
				if (cond1)self->state.flush = true;
				if (cond1 || cond2)
				{
					vector<vector<string>> res;
					bool isgood = true;
					for (int i = 0; i < batch_size; ++i)
					{
						for (auto& j : arr)
						{
							int idx = j.GetInt();
							if (inputs.find(idx) != inputs.end())
							{
								if (!inputs[idx].good()) { isgood = false; break; }
								string str;
								getline(inputs[idx], str);
								res.push_back(split(str, ','));
							}
							else
							{
								if (pending_res[idx].empty()) { isgood = false; break; }
								res.push_back(move(pending_res[idx].back()));
								pending_res[idx].pop_back();
							}
						}
						if (!isgood)
							break;
					}
					return { v["id"].GetInt(),move(res)};
				}
			}
		}
		return { -1,{{""}} };
	}

	Agent::behavior_type agent_behavior(Agent::stateful_pointer<agent_state> self)
	{
		return 
		{
			[=](publish_atom,int num_workers,int batch_size)
			{
				self->state.batch_size = batch_size;
				self->state.workers.reserve(num_workers);
				self->state.idle_workers.reserve(num_workers);
				auto res=self->system().middleman().publish(self,0);
				if (!res)aout(self) << "publish failed" << endl;
				else aout(self) << "Agent published at " <<*res<< endl;
				return result<continue_atom, int, string, uint16_t>(continue_atom::value, num_workers, hostname, *res);
			},
			[=](register_atom, actor_addr addr)
			{
				auto worker = actor_cast<Worker>(addr);
				self->monitor(worker);
				self->state.workers.insert(worker);
				return result<init_atom, string>(init_atom::value, self->state.json_str);
			},
			[=](work_atom,const string& task)
			{
				Parse(self, task);
			},
			[=](response_atom,int start,int idx,const vector<vector<string>>& response)
			{
				self->state.still_working[start]--;
				self->state.pending_res[idx].insert(self->state.pending_res[idx].end(),response.begin(),response.end());
				auto next_work = SelectWork(self);
				if (self->state.flush)
				{
					aout(self) << "enter flush!" << endl;
					for (auto i=self->state.idle_workers.begin();i!=self->state.idle_workers.end();)
					{
						auto work = SelectWork(self);
						if (work.first == -1)break;
						self->send(*i, work_atom::value, work.first, work.second);
						self->state.workers.insert(*i);
						i = self->state.idle_workers.erase(i);
					}
					self->state.flush = false;
				}
				if (next_work.first==-1)
				{
					auto worker = actor_cast<Worker>(self->current_sender());
					self->state.idle_workers.insert(worker);
					self->state.workers.erase(worker);
				}
				if (self->state.workers.empty())
				{
					for (auto i : self->state.still_working)
						aout(self) << i.first << " " << i.second << endl;
					aout(self) << "Work finished!" << endl;
					for (auto& worker : self->state.idle_workers)
						anon_send_exit(worker, exit_reason::kill);
					self->quit(exit_reason::normal);
				}
				if(next_work.first!=-1)
					self->state.still_working[next_work.first]++;
				return make_tuple(work_atom::value, next_work.first,next_work.second);
			},
			[=](request_atom, int start)
			{
				if (start != -1)self->state.still_working[start]--;
				auto next_work = SelectWork(self);
				if (next_work.first==-1)
				{
					auto worker = actor_cast<Worker>(self->current_sender());
					self->state.idle_workers.insert(worker);
					self->state.workers.erase(worker);
				}
				if (self->state.workers.empty())
				{
					aout(self) << "Work finished!" << endl;
					for (auto& worker : self->state.idle_workers)
						self->send_exit(worker, exit_reason::kill);
					self->quit(exit_reason::normal);
				}
				if(next_work.first!=-1)
					self->state.still_working[next_work.first]++;
				return make_tuple(work_atom::value,next_work.first, next_work.second);
			},
			[=](pause_atom)
			{
				unordered_set<Worker> fails;
				for (auto i : self->state.workers)
					self->request(i, chrono::milliseconds(200), pause_atom::value).await(EMPTY_LAMBDA, [&](const error& err) {fails.insert(i); aout(self) << self->system().render(err) << endl; });
				while (!fails.empty())
				{
					auto i=fails.begin();
					self->request(*i, chrono::milliseconds(200), pause_atom::value).await([&]() {fails.erase(i); }, [&](const error& err) {aout(self) << self->system().render(err) << endl; });
				}
				aout(self) << "all Workers paused" << endl;
			},
			[=](resume_atom)
			{
				unordered_set<Worker> fails;
				for (auto i : self->state.workers)
					self->request(i, chrono::milliseconds(200), resume_atom::value).await(EMPTY_LAMBDA, [&](const error& err) {fails.insert(i); aout(self) << self->system().render(err) << endl; });
				while (!fails.empty())
				{
					auto i = fails.begin();
					self->request(*i, chrono::milliseconds(200), resume_atom::value).await([&]() {fails.erase(i); }, [&](const error& err) {aout(self) << self->system().render(err) << endl; });
				}
				aout(self) << "all Workers resumed" << endl;
			}
		};
	}
}