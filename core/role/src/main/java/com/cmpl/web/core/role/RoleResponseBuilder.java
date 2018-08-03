package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.Builder;

public class RoleResponseBuilder extends Builder<RoleResponse> {

  private RoleDTO role;

  public RoleResponseBuilder role(RoleDTO role) {
    this.role = role;
    return this;
  }

  private RoleResponseBuilder() {

  }

  @Override
  public RoleResponse build() {
    RoleResponse response = new RoleResponse();
    response.setRole(role);

    return response;
  }

  public static RoleResponseBuilder create() {
    return new RoleResponseBuilder();
  }
}
