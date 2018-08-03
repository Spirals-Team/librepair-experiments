package com.cmpl.web.core.sitemap;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Sitemap;

public interface SitemapDAO extends BaseDAO<Sitemap> {

  List<Sitemap> findByWebsiteId(Long websiteId);

  List<Sitemap> findByPageId(Long pageId);

  Sitemap findByWebsiteIdAndPageId(Long websiteId, Long pageId);

}
