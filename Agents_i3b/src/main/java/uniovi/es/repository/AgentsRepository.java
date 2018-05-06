package uniovi.es.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uniovi.es.entities.Agent;

@Repository
public interface AgentsRepository extends MongoRepository<Agent, String> {

}
