package com.pufose.server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Service
@Repository
public interface IMongoRepository extends MongoRepository<DatabaseGrid,Integer> {

	public List<DatabaseGrid> findAll();

	public DatabaseGrid findById(int id);
	



	
	
}
