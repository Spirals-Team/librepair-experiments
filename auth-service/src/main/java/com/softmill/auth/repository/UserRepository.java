package com.softmill.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softmill.auth.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
