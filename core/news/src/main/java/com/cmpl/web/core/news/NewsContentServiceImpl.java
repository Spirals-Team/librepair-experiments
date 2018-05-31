package com.cmpl.web.core.news;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Implementation de l'interface pour la gestion de NewsContent
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsContents")
public class NewsContentServiceImpl extends BaseServiceImpl<NewsContentDTO, NewsContent> implements NewsContentService {

  public NewsContentServiceImpl(ApplicationEventPublisher publisher, NewsContentRepository newsContentRepository) {
    super(newsContentRepository, publisher);
  }

  @Override
  protected NewsContentDTO toDTO(NewsContent entity) {

    NewsContentDTO dto = new NewsContentDTO();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  protected NewsContent toEntity(NewsContentDTO dto) {

    NewsContent entity = new NewsContent();
    fillObject(dto, entity);

    return entity;
  }

}
