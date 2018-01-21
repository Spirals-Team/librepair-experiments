package com.pufose.server;

import java.util.List;

public interface IServiceImplementor {


	public List<String> getAllId();

	public DatabaseGrid getById(int id);

	public void storeInDb(DatabaseGrid grid);

	public List<DatabaseGrid> getAllGrids();

	public int nextId();

	public void dropTable(int id);

	

}
