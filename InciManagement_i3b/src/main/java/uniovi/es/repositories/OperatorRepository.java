package uniovi.es.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import uniovi.es.entities.Operator;

public interface OperatorRepository extends MongoRepository<Operator, String>{
}
