package com.cmpl.web.core.widget;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.exception.BaseException;

public class WidgetDispatcherImpl implements WidgetDispatcher {

  private final WidgetTranslator translator;
  private final WidgetValidator validator;
  private final WidgetService widgetService;
  private final WidgetPageService widgetPageService;

  public WidgetDispatcherImpl(WidgetTranslator translator, WidgetValidator validator, WidgetService widgetService,
      WidgetPageService widgetPageService) {
    this.translator = translator;
    this.validator = validator;
    this.widgetService = widgetService;
    this.widgetPageService = widgetPageService;

  }

  @Override
  public WidgetResponse createEntity(WidgetCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return WidgetResponseBuilder.create().error(error).build();
    }

    WidgetDTO widgetToCreate = translator.fromCreateFormToDTO(form);
    WidgetDTO createdWidget = widgetService.createEntity(widgetToCreate, form.getLocaleCode());
    return translator.fromDTOToResponse(createdWidget);
  }

  @Override
  public WidgetResponse updateEntity(WidgetUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return WidgetResponseBuilder.create().error(error).build();
    }

    WidgetDTO widgetToUpdate = widgetService.getEntity(form.getId());
    widgetToUpdate.setName(form.getName());
    widgetToUpdate.setPersonalization(form.getPersonalization());
    widgetToUpdate.setType(form.getType());
    widgetToUpdate.setEntityId(form.getEntityId());

    WidgetDTO updatedWidget = widgetService.updateEntity(widgetToUpdate, form.getLocaleCode());

    return translator.fromDTOToResponse(updatedWidget);
  }

  @Override
  public WidgetResponse deleteEntity(String widgetId, Locale locale) {
    widgetService.deleteEntity(Long.parseLong(widgetId));
    return WidgetResponseBuilder.create().build();
  }

  @Override
  public WidgetPageResponse createEntity(String pageId, WidgetPageCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return WidgetPageResponseBuilder.create().error(error).build();
    }

    WidgetPageDTO widgetPageToCreateToCreate = translator.fromCreateFormToDTO(form);
    WidgetPageDTO createdWidgetPageToCreate = widgetPageService.createEntity(widgetPageToCreateToCreate);
    return translator.fromDTOToResponse(createdWidgetPageToCreate);
  }

  @Override
  public void deleteEntity(String pageId, String widgetId, Locale locale) throws BaseException {

    Error error = validator.validateDelete(widgetId, locale);

    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }

    WidgetPageDTO widgetPageDTO = widgetPageService.findByPageIdAndWidgetId(pageId, widgetId);
    widgetPageService.deleteEntity(widgetPageDTO.getId());

  }
}
