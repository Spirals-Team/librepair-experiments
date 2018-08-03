package com.cmpl.web.core.sitemap;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class SitemapDTOBuilder extends BaseBuilder<SitemapDTO> {

  private Long websiteId;
  private Long pageId;

  public SitemapDTOBuilder websiteId(Long websiteId) {
    this.websiteId = websiteId;
    return this;
  }

  public SitemapDTOBuilder pageId(Long pageId) {
    this.pageId = pageId;
    return this;
  }

  private SitemapDTOBuilder() {

  }

  @Override
  public SitemapDTO build() {
    SitemapDTO design = new SitemapDTO();
    design.setPageId(pageId);
    design.setWebsiteId(websiteId);
    design.setCreationDate(creationDate);
    design.setCreationUser(creationUser);
    design.setModificationUser(modificationUser);
    design.setId(id);
    design.setModificationDate(modificationDate);
    return design;
  }

  public static SitemapDTOBuilder create() {
    return new SitemapDTOBuilder();
  }
}
