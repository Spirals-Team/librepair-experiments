package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.builder.Builder;

public class SitemapCreateFormBuilder extends Builder<SitemapCreateForm> {

  private String websiteId;
  private String pageId;

  public SitemapCreateFormBuilder websiteId(String websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public SitemapCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  private SitemapCreateFormBuilder() {

  }

  @Override
  public SitemapCreateForm build() {
    SitemapCreateForm form = new SitemapCreateForm();
    form.setWebsiteId(websiteId);
    form.setPageId(pageId);
    return form;
  }

  public static SitemapCreateFormBuilder create() {
    return new SitemapCreateFormBuilder();
  }
}
