package com.cmpl.web.core.role;

public interface RoleTranslator {

  RoleDTO fromCreateFormToDTO(RoleCreateForm form);

  RoleDTO fromUpdateFormToDTO(RoleUpdateForm form);

  RoleResponse fromDTOToResponse(RoleDTO dto);

}
