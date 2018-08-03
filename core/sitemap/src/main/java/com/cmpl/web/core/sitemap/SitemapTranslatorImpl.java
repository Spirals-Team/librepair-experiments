package com.cmpl.web.core.sitemap;

public class SitemapTranslatorImpl implements SitemapTranslator {

  @Override
  public SitemapDTO fromCreateFormToDTO(SitemapCreateForm form) {
    return SitemapDTOBuilder.create().websiteId(Long.parseLong(form.getWebsiteId()))
        .pageId(Long.parseLong(form.getPageId())).build();
  }

  @Override
  public SitemapResponse fromDTOToResponse(SitemapDTO dto) {
    return SitemapResponseBuilder.create().designDTO(dto).build();
  }
}
