package com.cmpl.web.core.news.entry;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageService;

public class NewsEntryMapper extends BaseMapper<NewsEntryDTO, NewsEntry> {

  private final NewsContentService newsContentService;
  private final NewsImageService newsImageService;

  public NewsEntryMapper(NewsContentService newsContentService, NewsImageService newsImageService) {
    this.newsContentService = newsContentService;
    this.newsImageService = newsImageService;
  }

  @Override
  public NewsEntryDTO toDTO(NewsEntry entity) {
    NewsEntryDTO dto = new NewsEntryDTO();
    fillObject(entity, dto);

    if (StringUtils.hasText(entity.getContentId())) {
      dto.setNewsContent(newsContentService.getEntity(Long.parseLong(entity.getContentId())));
    }

    if (StringUtils.hasText(entity.getImageId())) {
      dto.setNewsImage(newsImageService.getEntity(Long.parseLong(entity.getImageId())));
    }

    dto.setName(entity.getTitle());

    return dto;
  }

  @Override
  public NewsEntry toEntity(NewsEntryDTO dto) {

    NewsEntry entity = new NewsEntry();
    fillObject(dto, entity);

    if (dto.getNewsContent() != null) {
      entity.setContentId(String.valueOf(dto.getNewsContent().getId()));
    }

    if (dto.getNewsImage() != null) {
      entity.setImageId(String.valueOf(dto.getNewsImage().getId()));
    }

    return entity;
  }
}
