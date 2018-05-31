package com.cmpl.web.core.user;

import com.cmpl.web.core.common.resource.BaseResponse;

public class UserResponse extends BaseResponse {

  private UserDTO user;

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }
}
