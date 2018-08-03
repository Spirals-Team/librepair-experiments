package com.cmpl.web.core.design;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.exception.BaseException;

public class DesignDispatcherImpl implements DesignDispatcher {

  private final DesignService service;

  private final DesignTranslator translator;

  public DesignDispatcherImpl(DesignService service, DesignTranslator translator) {

    this.service = Objects.requireNonNull(service);

    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public DesignResponse createEntity(DesignCreateForm createForm, Locale locale) throws BaseException {

    DesignDTO designDTOToCreate = translator.fromCreateFormToDTO(createForm);
    DesignDTO createdDesignDTO = service.createEntity(designDTOToCreate);

    return translator.fromDTOToResponse(createdDesignDTO);
  }

  @Override
  public void deleteEntity(Long websiteId, Long styleId) throws BaseException {

    DesignDTO designDTO = service.findByWebsiteIdAndStyleId(websiteId, styleId);
    service.deleteEntity(designDTO.getId());
  }
}
