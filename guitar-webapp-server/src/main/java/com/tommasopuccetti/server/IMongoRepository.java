package com.tommasopuccetti.server;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMongoRepository extends MongoRepository<Media, String> {

}
