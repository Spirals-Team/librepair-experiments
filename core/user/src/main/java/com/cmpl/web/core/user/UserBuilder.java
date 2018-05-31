package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class UserBuilder extends BaseBuilder<User> {

  private String login;
  private String password;
  private LocalDateTime lastConnection;
  private String email;
  private String description;
  private LocalDateTime lastPasswordModification;

  private UserBuilder() {

  }

  public UserBuilder login(String login) {
    this.login = login;
    return this;
  }

  public UserBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder lastConnection(LocalDateTime lastConnection) {
    this.lastConnection = lastConnection;
    return this;
  }

  public UserBuilder lastPasswordModification(LocalDateTime lastPasswordModification) {
    this.lastPasswordModification = lastPasswordModification;
    return this;
  }

  public UserBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder description(String description) {
    this.description = description;
    return this;
  }

  @Override
  public User build() {
    User user = new User();
    user.setDescription(description);
    user.setEmail(email);
    user.setLastConnection(lastConnection);
    user.setLastPasswordModification(lastPasswordModification);
    user.setLogin(login);
    user.setPassword(password);
    return user;
  }

  public static UserBuilder create() {
    return new UserBuilder();
  }

}
