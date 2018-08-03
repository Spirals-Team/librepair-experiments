package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;

public class UserResponseBuilder extends Builder<UserResponse> {

  private UserDTO user;

  private UserResponseBuilder() {

  }

  public UserResponseBuilder user(UserDTO page) {
    this.user = page;
    return this;
  }

  @Override
  public UserResponse build() {
    UserResponse response = new UserResponse();
    response.setUser(user);
    return response;
  }

  public static UserResponseBuilder create() {
    return new UserResponseBuilder();
  }
}
