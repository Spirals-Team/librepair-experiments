package com.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Agent;

@Repository
public interface AgentInfoRepository extends MongoRepository<Agent, String> {
	Agent findById(String id);

}
