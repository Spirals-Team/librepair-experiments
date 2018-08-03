package com.cmpl.web.core.role.privilege;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class PrivilegeForm {

  private String roleId;
  @NotEmpty(message = "empty.role.privileges")
  private List<String> privilegesToEnable;

  public PrivilegeForm() {

  }

  public PrivilegeForm(List<String> privilegesToEnable) {
    this.privilegesToEnable = new ArrayList<>();
    this.privilegesToEnable.addAll(privilegesToEnable);
  }

  public List<String> getPrivilegesToEnable() {
    return privilegesToEnable;
  }

  public void setPrivilegesToEnable(List<String> privilegesToEnable) {
    this.privilegesToEnable = privilegesToEnable;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
}
