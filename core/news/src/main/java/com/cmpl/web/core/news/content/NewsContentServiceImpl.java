package com.cmpl.web.core.news.content;

import org.springframework.cache.annotation.CacheConfig;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.NewsContent;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsContents")
public class NewsContentServiceImpl extends BaseServiceImpl<NewsContentDTO, NewsContent> implements NewsContentService {

  public NewsContentServiceImpl(NewsContentDAO newsContentDAO, NewsContentMapper newsContentMapper) {
    super(newsContentDAO, newsContentMapper);
  }

}
