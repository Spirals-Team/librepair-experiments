package com.pufose.server;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mongo")
public class MongoImplementor implements IServiceImplementor {

	@Autowired
	private IMongoRepository repo;

	@Override
	public List<String> getAllId() {
		List<DatabaseGrid> list = repo.findAll();
		List<String> toreturn = new LinkedList<>();
		for (DatabaseGrid grid : list)
			toreturn.add("" + grid.getId());
		return toreturn;
	}

	@Override
	public DatabaseGrid getById(int id) {
		return repo.findById(id);
	}

	@Override
	public void storeInDb(DatabaseGrid grid) {
		repo.save(grid);

	}

	@Override
	public List<DatabaseGrid> getAllGrids() {
		return repo.findAll();
	}

	@Override
	public int nextId() {
		List<DatabaseGrid> allGrids = repo.findAll();
		if (allGrids.isEmpty())
			return 1;
		int maxid = allGrids.get(allGrids.size() - 1).getId();
		return maxid + 1;
	}

	@Override
	public void dropTable(int id) {
		repo.delete(id);

	}



}
