package com.cmpl.web.core.page;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public class PageDispatcherImpl implements PageDispatcher {

  private final PageValidator validator;
  private final PageTranslator translator;
  private final PageService pageService;

  public PageDispatcherImpl(PageValidator validator, PageTranslator translator, PageService pageService) {
    this.validator = validator;
    this.translator = translator;
    this.pageService = pageService;
  }

  @Override
  public PageResponse createEntity(PageCreateForm form, Locale locale) {

    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return PageResponseBuilder.create().error(error).build();
    }

    PageDTO pageToCreate = translator.fromCreateFormToDTO(form);
    PageDTO createdPage = pageService.createEntity(pageToCreate, form.getLocaleCode());
    return translator.fromDTOToResponse(createdPage);
  }

  @Override
  public PageResponse updateEntity(PageUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return PageResponseBuilder.create().error(error).build();
    }

    PageDTO pageToUpdate = pageService.getEntity(form.getId(), form.getLocaleCode());
    pageToUpdate.setBody(form.getBody());
    pageToUpdate.setFooter(form.getFooter());
    pageToUpdate.setHeader(form.getHeader());
    pageToUpdate.setMenuTitle(form.getMenuTitle());
    pageToUpdate.setName(form.getName());
    pageToUpdate.setMeta(form.getMeta());

    PageDTO updatedPage = pageService.updateEntity(pageToUpdate, form.getLocaleCode());

    return translator.fromDTOToResponse(updatedPage);
  }

  @Override
  public PageResponse deleteEntity(String pageId, Locale locale) {
    pageService.deleteEntity(Long.parseLong(pageId));
    return PageResponseBuilder.create().build();
  }

}
