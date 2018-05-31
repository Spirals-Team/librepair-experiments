package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class AssociationUserRoleBuilder extends BaseBuilder<AssociationUserRole> {

  private String userId;
  private String roleId;

  public AssociationUserRoleBuilder userId(String userId) {
    this.userId = userId;
    return this;
  }

  public AssociationUserRoleBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private AssociationUserRoleBuilder() {

  }

  @Override
  public AssociationUserRole build() {
    AssociationUserRole associationUserRole = new AssociationUserRole();
    associationUserRole.setRoleId(roleId);
    associationUserRole.setUserId(userId);
    return associationUserRole;
  }

  public static AssociationUserRoleBuilder create() {
    return new AssociationUserRoleBuilder();
  }
}
