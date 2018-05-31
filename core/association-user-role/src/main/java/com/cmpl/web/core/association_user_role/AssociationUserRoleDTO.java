package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.dto.BaseDTO;

public class AssociationUserRoleDTO extends BaseDTO {

  private String userId;
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
