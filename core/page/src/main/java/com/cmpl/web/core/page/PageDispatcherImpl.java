package com.cmpl.web.core.page;

import java.util.Locale;
import java.util.Objects;

public class PageDispatcherImpl implements PageDispatcher {

  private final PageTranslator translator;
  private final PageService pageService;

  public PageDispatcherImpl(PageTranslator translator, PageService pageService) {
    this.translator = Objects.requireNonNull(translator);
    this.pageService = Objects.requireNonNull(pageService);
  }

  @Override
  public PageResponse createEntity(PageCreateForm form, Locale locale) {

    PageDTO pageToCreate = translator.fromCreateFormToDTO(form);
    PageDTO createdPage = pageService.createEntity(pageToCreate, form.getLocaleCode());
    return translator.fromDTOToResponse(createdPage);
  }

  @Override
  public PageResponse updateEntity(PageUpdateForm form, Locale locale) {

    PageDTO pageToUpdate = pageService.getEntity(form.getId(), form.getLocaleCode());
    pageToUpdate.setBody(form.getBody());
    pageToUpdate.setFooter(form.getFooter());
    pageToUpdate.setHeader(form.getHeader());
    pageToUpdate.setMenuTitle(form.getMenuTitle());
    pageToUpdate.setName(form.getName());
    pageToUpdate.setMeta(form.getMeta());
    pageToUpdate.setAmp(form.getAmp());

    PageDTO updatedPage = pageService.updateEntity(pageToUpdate, form.getLocaleCode());

    return translator.fromDTOToResponse(updatedPage);
  }

  @Override
  public PageResponse deleteEntity(String pageId, Locale locale) {
    pageService.deleteEntity(Long.parseLong(pageId));
    return PageResponseBuilder.create().build();
  }

}
