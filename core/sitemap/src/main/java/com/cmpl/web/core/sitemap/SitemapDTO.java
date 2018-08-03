package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.dto.BaseDTO;

public class SitemapDTO extends BaseDTO {

  private Long websiteId;
  private Long pageId;

  public Long getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(Long websiteId) {
    this.websiteId = websiteId;
  }

  public Long getPageId() {
    return pageId;
  }

  public void setPageId(Long pageId) {
    this.pageId = pageId;
  }
}
