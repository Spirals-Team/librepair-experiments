package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class RoleResponseBuilder extends Builder<RoleResponse> {

  private RoleDTO role;
  private Error error;

  public RoleResponseBuilder role(RoleDTO role) {
    this.role = role;
    return this;
  }

  public RoleResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  private RoleResponseBuilder() {

  }

  @Override
  public RoleResponse build() {
    RoleResponse response = new RoleResponse();
    response.setRole(role);
    response.setError(error);

    return response;
  }

  public static RoleResponseBuilder create() {
    return new RoleResponseBuilder();
  }
}
