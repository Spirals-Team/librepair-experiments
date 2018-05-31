package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class ChangePasswordResponseBuilder extends Builder<ChangePasswordResponse> {

  private Error error;

  private ChangePasswordResponseBuilder() {

  }

  public ChangePasswordResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public ChangePasswordResponse build() {
    ChangePasswordResponse response = new ChangePasswordResponse();
    response.setError(error);
    return response;
  }

  public static ChangePasswordResponseBuilder create() {
    return new ChangePasswordResponseBuilder();
  }
}
