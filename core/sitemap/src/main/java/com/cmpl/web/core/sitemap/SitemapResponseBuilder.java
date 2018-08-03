package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.builder.Builder;

public class SitemapResponseBuilder extends Builder<SitemapResponse> {

  private SitemapDTO sitemapDTO;

  public SitemapResponseBuilder designDTO(SitemapDTO sitemapDTO) {
    this.sitemapDTO = sitemapDTO;
    return this;
  }

  private SitemapResponseBuilder() {

  }

  @Override
  public SitemapResponse build() {
    SitemapResponse sitemapResponse = new SitemapResponse();
    sitemapResponse.setSitemapDTO(sitemapDTO);
    return sitemapResponse;
  }

  public static SitemapResponseBuilder create() {
    return new SitemapResponseBuilder();
  }
}
