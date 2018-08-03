package com.cmpl.web.core.sitemap;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface SitemapService extends BaseService<SitemapDTO> {

  List<SitemapDTO> findByWebsiteId(Long websiteId);

  List<SitemapDTO> findByPageId(Long pageId);

  SitemapDTO findByWebsiteIdAndPageId(Long websiteId, Long pageId);

}
