package com.cmpl.web.core.role;

import java.util.ArrayList;
import java.util.List;

public class PrivilegeForm {

  private String roleId;
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
