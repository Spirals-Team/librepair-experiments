package com.cmpl.web.core.news.content;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.NewsContent;

public class NewsContentMapper extends BaseMapper<NewsContentDTO, NewsContent> {

  @Override
  public NewsContentDTO toDTO(NewsContent entity) {
    NewsContentDTO dto = new NewsContentDTO();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  public NewsContent toEntity(NewsContentDTO dto) {
    NewsContent entity = new NewsContent();
    fillObject(dto, entity);

    return entity;
  }
}
