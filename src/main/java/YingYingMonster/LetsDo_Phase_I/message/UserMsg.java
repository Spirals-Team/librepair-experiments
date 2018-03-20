package YingYingMonster.LetsDo_Phase_I.message;

import YingYingMonster.LetsDo_Phase_I.model.User;

public class UserMsg extends Msg {

	private User user;
	private Results results;
	private Reasons reasons;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public Reasons getReasons() {
		return reasons;
	}

	public void setReasons(Reasons reasons) {
		this.reasons = reasons;
	}
	
	
}
