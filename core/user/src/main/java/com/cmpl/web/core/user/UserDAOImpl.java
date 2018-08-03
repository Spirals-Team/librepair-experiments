package com.cmpl.web.core.user;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.User;

public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {

  private final UserRepository userRepository;

  public UserDAOImpl(UserRepository entityRepository, ApplicationEventPublisher publisher) {
    super(User.class, entityRepository, publisher);
    this.userRepository = entityRepository;
  }

  @Override
  public User findByLogin(String login) {
    return userRepository.findByLogin(login);
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
