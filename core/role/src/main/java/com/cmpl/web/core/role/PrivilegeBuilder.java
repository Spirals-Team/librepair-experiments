package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class PrivilegeBuilder extends BaseBuilder<Privilege> {

  private String roleId;

  private String content;

  private PrivilegeBuilder() {

  }

  public PrivilegeBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  public PrivilegeBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public Privilege build() {
    Privilege privilege = new Privilege();
    privilege.setContent(content);
    privilege.setRoleId(roleId);
    return privilege;
  }

  public static PrivilegeBuilder create() {
    return new PrivilegeBuilder();
  }
}
