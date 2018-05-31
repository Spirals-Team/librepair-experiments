package com.cmpl.web.core.association_user_role;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class AssociationUserRoleDTOBuilder extends BaseBuilder<AssociationUserRoleDTO> {

  private String userId;
  private String roleId;

  public AssociationUserRoleDTOBuilder userId(String userId) {
    this.userId = userId;
    return this;
  }

  public AssociationUserRoleDTOBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  private AssociationUserRoleDTOBuilder() {

  }

  @Override
  public AssociationUserRoleDTO build() {
    AssociationUserRoleDTO associationUserRole = new AssociationUserRoleDTO();
    associationUserRole.setRoleId(roleId);
    associationUserRole.setUserId(userId);
    return associationUserRole;
  }

  public static AssociationUserRoleDTOBuilder create() {
    return new AssociationUserRoleDTOBuilder();
  }
}
