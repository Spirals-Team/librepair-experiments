package com.cmpl.web.core.sitemap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Sitemap;

@Repository
public interface SitemapRepository extends BaseRepository<Sitemap> {

  List<Sitemap> findByWebsiteId(Long websiteId);

  List<Sitemap> findByPageId(Long pageId);

  Sitemap findByWebsiteIdAndPageId(Long websiteId, Long pageId);

}
