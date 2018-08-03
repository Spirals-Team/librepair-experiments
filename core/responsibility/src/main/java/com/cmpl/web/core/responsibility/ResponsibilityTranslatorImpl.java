package com.cmpl.web.core.responsibility;

public class ResponsibilityTranslatorImpl implements ResponsibilityTranslator {

  @Override
  public ResponsibilityDTO fromCreateFormToDTO(ResponsibilityCreateForm form) {
    return ResponsibilityDTOBuilder.create().roleId(form.getRoleId()).userId(form.getUserId()).build();
  }

  @Override
  public ResponsibilityResponse fromDTOToResponse(ResponsibilityDTO dto) {
    return ResponsibilityResponseBuilder.create().associationUserRoleDTO(dto).build();
  }
}
