package com.cmpl.web.core.association_user_role;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.exception.BaseException;

public class AssociationUserRoleDispatcherImpl implements AssociationUserRoleDispatcher {

  private final AssociationUserRoleService service;
  private final AssociationUserRoleValidator validator;
  private final AssociationUserRoleTranslator translator;

  public AssociationUserRoleDispatcherImpl(AssociationUserRoleService service, AssociationUserRoleValidator validator,
      AssociationUserRoleTranslator translator) {
    this.service = service;
    this.translator = translator;
    this.validator = validator;
  }

  @Override
  public AssociationUserRoleResponse createEntity(AssociationUserRoleCreateForm createForm, Locale locale)
      throws BaseException {

    Error error = validator.validateCreate(createForm, locale);

    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }

    AssociationUserRoleDTO associationUserRoleDTOToCreate = translator.fromCreateFormToDTO(createForm);
    AssociationUserRoleDTO createdAssociationUserRoleDTO = service.createEntity(associationUserRoleDTOToCreate);

    return translator.fromDTOToResponse(createdAssociationUserRoleDTO);
  }

  @Override
  public void deleteEntity(String userId, String roleId, Locale locale) throws BaseException {

    Error error = validator.validateDelete(userId, roleId, locale);

    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }

    AssociationUserRoleDTO associationUserRoleDTO = service.findByUserIdAndRoleId(userId, roleId);
    service.deleteEntity(associationUserRoleDTO.getId());
  }
}
