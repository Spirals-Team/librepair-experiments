package com.revature.project2.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  /**
   * find the user by email
   * @param email user's email
   * @return user who has param email
   */
  User findByEmail(String email);

  /**
   * find the user by username
   * @param username user's username
   * @return user who has param username
   */
  User findByUsername(String username);

}
