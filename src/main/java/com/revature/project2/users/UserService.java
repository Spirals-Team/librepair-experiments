package com.revature.project2.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User service to provide connection between dao and controller
 */
@Service
public class UserService {
  /**
   * User Repository
   */
  private final UserRepository userRepository;

  /**
   * Autowired user repository into the user service
   * @param userRepository
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public boolean isExistUsername(String username){
    return userRepository.existsByUsername(username);
  }
  /**
   * find user by userid
   * @param id user id
   * @return Optional that contain user who has exact user id
   */
  public Optional<User> findByUserId(int id) {
    System.out.println("findbyuserid");
    return userRepository.findById(id);
}

  /**
   * fetch all user that this application have
   * @return list of users
   */
  public Iterable<User> findAllUsers() {
    return userRepository.findAll();
  }

  /**
   * find user by username
   * @param username users's username
   * @return user object
   */
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  /**
   * save the user
   * @param user user object
   * @return user object
   */
  public User save(User user) {
    return userRepository.save(user);
  }

  /**
   * find user by email
   * @param email users' email
   * @return user who have exact email
   */
  public User loadByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Update user
   * @param user new user information
   * @return the user that has been changed
   */
  public User updateUser(User user) {
    return userRepository.save(user);
  }
}
