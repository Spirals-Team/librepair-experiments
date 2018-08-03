package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;

public class ChangePasswordResponseBuilder extends Builder<ChangePasswordResponse> {

  private ChangePasswordResponseBuilder() {

  }

  @Override
  public ChangePasswordResponse build() {
    ChangePasswordResponse response = new ChangePasswordResponse();
    return response;
  }

  public static ChangePasswordResponseBuilder create() {
    return new ChangePasswordResponseBuilder();
  }
}
