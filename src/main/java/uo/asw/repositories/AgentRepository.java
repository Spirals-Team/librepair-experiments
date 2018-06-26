package uo.asw.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uo.asw.entities.Agent;



@Repository
public interface AgentRepository extends MongoRepository<Agent, ObjectId> {
	
	
	public Agent findByEmail(String email);
	
}
