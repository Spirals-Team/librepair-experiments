package by.imsha.repository;

import by.imsha.domain.Parish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 *
 */
public interface ParishRepository extends QuerableMongoRepository<Parish, String>{
    public Parish findByUserId(String userId);
}
