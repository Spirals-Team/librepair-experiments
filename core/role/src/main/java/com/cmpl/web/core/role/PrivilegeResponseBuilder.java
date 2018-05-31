package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class PrivilegeResponseBuilder extends Builder<PrivilegeResponse> {

  private Error error;

  private PrivilegeResponseBuilder() {

  }

  public PrivilegeResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public PrivilegeResponse build() {
    PrivilegeResponse response = new PrivilegeResponse();
    response.setError(error);
    return response;
  }

  public static PrivilegeResponseBuilder create() {
    return new PrivilegeResponseBuilder();
  }
}
