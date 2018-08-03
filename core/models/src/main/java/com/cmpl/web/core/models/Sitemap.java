package com.cmpl.web.core.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity(name = "sitemap")
@Table(name = "sitemap", indexes = {@Index(name = "IDX_SITEMAP", columnList = "website_id"),
    @Index(name = "IDX_PAGE", columnList = "page_id"),
    @Index(name = "IDX_WEBSITE_PAGE", columnList = "page_id,website_id")})
public class Sitemap extends BaseEntity {

  @Column(name = "page_id")
  private Long pageId;

  @Column(name = "website_id")
  private Long websiteId;

  public Long getPageId() {
    return pageId;
  }

  public void setPageId(Long pageId) {
    this.pageId = pageId;
  }

  public Long getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(Long websiteId) {
    this.websiteId = websiteId;
  }
}
