package com.cmpl.web.core.role;

import java.util.List;

import com.cmpl.web.core.common.builder.Builder;

public class PrivilegeFormBuilder extends Builder<PrivilegeForm> {

  private String roleId;
  private List<String> privilegesToEnable;

  public PrivilegeFormBuilder privilegesToEnable(List<String> privilegesToEnable) {
    this.privilegesToEnable = privilegesToEnable;
    return this;
  }

  public PrivilegeFormBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private PrivilegeFormBuilder() {

  }

  @Override
  public PrivilegeForm build() {
    PrivilegeForm form = new PrivilegeForm(privilegesToEnable);
    form.setRoleId(roleId);
    return form;
  }

  public static PrivilegeFormBuilder create() {
    return new PrivilegeFormBuilder();
  }
}
