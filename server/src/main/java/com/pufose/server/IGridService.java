package com.pufose.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class IGridService {
	
	@Autowired
	private IServiceImplementor impl;
	
	
	public void setImplementor(IServiceImplementor impl) {
		this.impl=impl;
	}
	
	public  List<String> getAllId(){
		return impl.getAllId();
	}
	public  DatabaseGrid getById(int id) {
		return impl.getById(id);
	}
	public  void storeInDb(DatabaseGrid grid) {
		impl.storeInDb(grid);
	}
	public  List<DatabaseGrid> getAllGrids(){
		return impl.getAllGrids();
	}
	public  int nextId() {
		return impl.nextId();
	}
	public  void dropTable(int id) {
		impl.dropTable(id);
	}
	public abstract List<String> getShortestPath(String from, String to, int id);

	

}
