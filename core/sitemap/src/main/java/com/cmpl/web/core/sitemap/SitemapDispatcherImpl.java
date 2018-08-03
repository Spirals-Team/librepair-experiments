package com.cmpl.web.core.sitemap;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.exception.BaseException;

public class SitemapDispatcherImpl implements SitemapDispatcher {

  private final SitemapService service;

  private final SitemapTranslator translator;

  public SitemapDispatcherImpl(SitemapService service, SitemapTranslator translator) {

    this.service = Objects.requireNonNull(service);

    this.translator = Objects.requireNonNull(translator);

  }

  @Override
  public SitemapResponse createEntity(SitemapCreateForm createForm, Locale locale) throws BaseException {

    SitemapDTO sitemapDTOToCreate = translator.fromCreateFormToDTO(createForm);
    SitemapDTO createdSitemapDTO = service.createEntity(sitemapDTOToCreate);

    return translator.fromDTOToResponse(createdSitemapDTO);
  }

  @Override
  public void deleteEntity(Long websiteId, Long pageId) throws BaseException {

    SitemapDTO sitemapDTO = service.findByWebsiteIdAndPageId(websiteId, pageId);
    service.deleteEntity(sitemapDTO.getId());
  }
}
