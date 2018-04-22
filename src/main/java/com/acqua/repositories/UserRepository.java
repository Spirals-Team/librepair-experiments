package com.acqua.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.acqua.entities.User;



/**
 * Repository for managing {@link User} object
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
	
    User findByUsername(String username);
    
    User findByUsernameAndPassword(String username, String password);
    
}