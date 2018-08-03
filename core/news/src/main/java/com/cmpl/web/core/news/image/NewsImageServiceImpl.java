package com.cmpl.web.core.news.image;

import org.springframework.cache.annotation.CacheConfig;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.NewsImage;

/**
 * Implementation de l'interface de gestion des NewsImage
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsImages")
public class NewsImageServiceImpl extends BaseServiceImpl<NewsImageDTO, NewsImage> implements NewsImageService {

  public NewsImageServiceImpl(NewsImageDAO newsImageDAO, NewsImageMapper newsImageMapper) {
    super(newsImageDAO, newsImageMapper);
  }

}
