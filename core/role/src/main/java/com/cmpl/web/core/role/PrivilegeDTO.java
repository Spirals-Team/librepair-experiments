package com.cmpl.web.core.role;

import com.cmpl.web.core.common.dto.BaseDTO;

public class PrivilegeDTO extends BaseDTO {

  private String roleId;

  private String content;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
