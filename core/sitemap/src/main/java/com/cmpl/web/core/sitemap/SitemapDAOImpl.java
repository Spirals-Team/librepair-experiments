package com.cmpl.web.core.sitemap;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Sitemap;

public class SitemapDAOImpl extends BaseDAOImpl<Sitemap> implements SitemapDAO {

  private final SitemapRepository sitemapRepository;

  public SitemapDAOImpl(SitemapRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Sitemap.class, entityRepository, publisher);
    this.sitemapRepository = entityRepository;
  }

  @Override
  public List<Sitemap> findByWebsiteId(Long websiteId) {
    return sitemapRepository.findByWebsiteId(websiteId);
  }

  @Override
  public List<Sitemap> findByPageId(Long pageId) {
    return sitemapRepository.findByPageId(pageId);
  }

  @Override
  public Sitemap findByWebsiteIdAndPageId(Long websiteId, Long pageId) {
    return sitemapRepository.findByWebsiteIdAndPageId(websiteId, pageId);
  }
}
