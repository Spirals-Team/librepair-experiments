package com.cmpl.web.core.role;

import com.cmpl.web.core.common.resource.BaseResponse;

public class RoleResponse extends BaseResponse {

  private RoleDTO role;

  public RoleDTO getRole() {
    return role;
  }

  public void setRole(RoleDTO role) {
    this.role = role;
  }
}
