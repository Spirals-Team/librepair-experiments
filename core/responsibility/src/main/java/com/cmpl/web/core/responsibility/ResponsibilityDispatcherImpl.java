package com.cmpl.web.core.responsibility;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.exception.BaseException;

public class ResponsibilityDispatcherImpl implements ResponsibilityDispatcher {

  private final ResponsibilityService service;

  private final ResponsibilityTranslator translator;

  public ResponsibilityDispatcherImpl(ResponsibilityService service,
      ResponsibilityTranslator translator) {

    this.service = Objects.requireNonNull(service);

    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public ResponsibilityResponse createEntity(ResponsibilityCreateForm createForm, Locale locale)
      throws BaseException {

    ResponsibilityDTO responsibilityDTOToCreate = translator.fromCreateFormToDTO(createForm);
    ResponsibilityDTO createdResponsibilityDTO = service.createEntity(responsibilityDTOToCreate);

    return translator.fromDTOToResponse(createdResponsibilityDTO);
  }

  @Override
  public void deleteEntity(String userId, String roleId, Locale locale) throws BaseException {

    ResponsibilityDTO responsibilityDTO = service.findByUserIdAndRoleId(userId, roleId);
    service.deleteEntity(responsibilityDTO.getId());
  }
}
