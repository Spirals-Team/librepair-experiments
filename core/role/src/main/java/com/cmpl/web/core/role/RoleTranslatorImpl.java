package com.cmpl.web.core.role;

public class RoleTranslatorImpl implements RoleTranslator {

  @Override
  public RoleDTO fromCreateFormToDTO(RoleCreateForm form) {
    return RoleDTOBuilder.create().description(form.getDescription()).name(form.getName()).build();
  }

  @Override
  public RoleDTO fromUpdateFormToDTO(RoleUpdateForm form) {
    return RoleDTOBuilder.create().name(form.getName()).description(form.getDescription()).build();
  }

  @Override
  public RoleResponse fromDTOToResponse(RoleDTO dto) {
    return RoleResponseBuilder.create().role(dto).build();
  }
}
