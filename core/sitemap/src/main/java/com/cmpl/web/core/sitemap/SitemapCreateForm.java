package com.cmpl.web.core.sitemap;

import javax.validation.constraints.NotBlank;

public class SitemapCreateForm {

  @NotBlank(message = "empty.website.id")
  private String websiteId;

  @NotBlank(message = "empty.page.id")
  private String pageId;

  public String getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(String websiteId) {
    this.websiteId = websiteId;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }
}
