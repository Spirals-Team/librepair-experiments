package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class UserResponseBuilder extends Builder<UserResponse> {

  private UserDTO user;
  private Error error;

  private UserResponseBuilder() {

  }

  public UserResponseBuilder user(UserDTO page) {
    this.user = page;
    return this;
  }

  public UserResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public UserResponse build() {
    UserResponse response = new UserResponse();
    response.setUser(user);
    response.setError(error);
    return response;
  }

  public static UserResponseBuilder create() {
    return new UserResponseBuilder();
  }
}
