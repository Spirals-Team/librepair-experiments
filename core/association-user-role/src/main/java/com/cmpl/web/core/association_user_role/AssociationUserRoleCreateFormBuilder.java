package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.builder.Builder;

public class AssociationUserRoleCreateFormBuilder extends Builder<AssociationUserRoleCreateForm> {

  private String userId;
  private String roleId;

  public AssociationUserRoleCreateFormBuilder userId(String userId) {
    this.userId = userId;
    return this;
  }

  public AssociationUserRoleCreateFormBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private AssociationUserRoleCreateFormBuilder() {

  }

  @Override
  public AssociationUserRoleCreateForm build() {
    AssociationUserRoleCreateForm form = new AssociationUserRoleCreateForm();
    form.setRoleId(roleId);
    form.setUserId(userId);
    return form;
  }

  public static AssociationUserRoleCreateFormBuilder create() {
    return new AssociationUserRoleCreateFormBuilder();
  }
}
