package com.cmpl.web.core.responsibility;

import javax.validation.constraints.NotBlank;

public class ResponsibilityCreateForm {

  @NotBlank(message = "empty.user.id")
  private String userId;

  @NotBlank(message = "empty.role.id")
  private String roleId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}
