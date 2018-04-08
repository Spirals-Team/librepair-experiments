package repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.Operator;

public interface OperatorRepository extends MongoRepository<Operator, String>{
}
