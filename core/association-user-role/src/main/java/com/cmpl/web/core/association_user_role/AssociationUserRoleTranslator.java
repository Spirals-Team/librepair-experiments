package com.cmpl.web.core.association_user_role;

public interface AssociationUserRoleTranslator {

  AssociationUserRoleDTO fromCreateFormToDTO(AssociationUserRoleCreateForm form);

  AssociationUserRoleResponse fromDTOToResponse(AssociationUserRoleDTO dto);

}
