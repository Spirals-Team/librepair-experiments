package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.builder.Builder;

public class PrivilegeResponseBuilder extends Builder<PrivilegeResponse> {

  private PrivilegeResponseBuilder() {

  }

  @Override
  public PrivilegeResponse build() {
    PrivilegeResponse response = new PrivilegeResponse();
    return response;
  }

  public static PrivilegeResponseBuilder create() {
    return new PrivilegeResponseBuilder();
  }
}
