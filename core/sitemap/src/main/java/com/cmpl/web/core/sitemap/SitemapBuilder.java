package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Sitemap;

public class SitemapBuilder extends BaseBuilder<Sitemap> {

  private Long websiteId;
  private Long pageId;

  public SitemapBuilder websiteId(Long websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public SitemapBuilder pageId(Long pageId) {
    this.pageId = pageId;
    return this;
  }

  private SitemapBuilder() {

  }

  @Override
  public Sitemap build() {
    Sitemap sitemap = new Sitemap();
    sitemap.setPageId(pageId);
    sitemap.setWebsiteId(websiteId);
    sitemap.setCreationDate(creationDate);
    sitemap.setCreationUser(creationUser);
    sitemap.setModificationUser(modificationUser);
    sitemap.setId(id);
    sitemap.setModificationDate(modificationDate);
    return sitemap;
  }

  public static SitemapBuilder create() {
    return new SitemapBuilder();
  }
}
