package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.resource.BaseResponse;

public class SitemapResponse extends BaseResponse {

  private SitemapDTO sitemapDTO;

  public SitemapDTO getSitemapDTO() {
    return sitemapDTO;
  }

  public void setSitemapDTO(SitemapDTO sitemapDTO) {
    this.sitemapDTO = sitemapDTO;
  }
}
