package com.cmpl.web.core.association_user_role;

public class AssociationUserRoleTranslatorImpl implements AssociationUserRoleTranslator {

  @Override
  public AssociationUserRoleDTO fromCreateFormToDTO(AssociationUserRoleCreateForm form) {
    return AssociationUserRoleDTOBuilder.create().roleId(form.getRoleId()).userId(form.getUserId()).build();
  }

  @Override
  public AssociationUserRoleResponse fromDTOToResponse(AssociationUserRoleDTO dto) {
    return AssociationUserRoleResponseBuilder.create().associationUserRoleDTO(dto).build();
  }
}
