package com.cmpl.web.core.user;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class UserDTOBuilder extends BaseBuilder<UserDTO> {

  private String login;
  private String password;
  private LocalDateTime lastConnection;
  private String email;
  private String description;
  private LocalDateTime lastPasswordModification;

  private UserDTOBuilder() {

  }

  public UserDTOBuilder login(String login) {
    this.login = login;
    return this;
  }

  public UserDTOBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserDTOBuilder lastConnection(LocalDateTime lastConnection) {
    this.lastConnection = lastConnection;
    return this;
  }

  public UserDTOBuilder lastPasswordModification(LocalDateTime lastPasswordModification) {
    this.lastPasswordModification = lastPasswordModification;
    return this;
  }

  public UserDTOBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  @Override
  public UserDTO build() {
    UserDTO user = new UserDTO();
    user.setDescription(description);
    user.setEmail(email);
    user.setLastConnection(lastConnection);
    user.setLogin(login);
    user.setPassword(password);
    user.setLastPasswordModification(lastPasswordModification);
    return user;
  }

  public static UserDTOBuilder create() {
    return new UserDTOBuilder();
  }
}
