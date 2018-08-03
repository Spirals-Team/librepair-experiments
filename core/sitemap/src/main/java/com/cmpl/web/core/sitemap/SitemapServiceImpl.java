package com.cmpl.web.core.sitemap;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Sitemap;

public class SitemapServiceImpl extends BaseServiceImpl<SitemapDTO, Sitemap> implements SitemapService {

  private final SitemapDAO sitemapDAO;

  public SitemapServiceImpl(SitemapDAO sitemapDAO, SitemapMapper sitemapMapper) {
    super(sitemapDAO, sitemapMapper);
    this.sitemapDAO = sitemapDAO;
  }

  @Override
  public List<SitemapDTO> findByWebsiteId(Long websiteId) {
    return mapper.toListDTO(sitemapDAO.findByWebsiteId(websiteId));
  }

  @Override
  public List<SitemapDTO> findByPageId(Long pageId) {
    return mapper.toListDTO(sitemapDAO.findByPageId(pageId));
  }

  @Override
  public SitemapDTO findByWebsiteIdAndPageId(Long websiteId, Long pageId) {
    return mapper.toDTO(sitemapDAO.findByWebsiteIdAndPageId(websiteId, pageId));
  }

}
