package com.cmpl.web.core.sitemap;

public interface SitemapTranslator {

  SitemapDTO fromCreateFormToDTO(SitemapCreateForm form);

  SitemapResponse fromDTOToResponse(SitemapDTO dto);

}
