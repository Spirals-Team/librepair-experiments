package com.rexam.dao;

import org.springframework.data.repository.CrudRepository;

import com.rexam.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	 User findByEmail(String email);
	
}
