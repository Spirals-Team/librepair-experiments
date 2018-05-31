package com.cmpl.web.core.news;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

/**
 * Implementation de l'interface de gestion des NewsImage
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "newsImages")
public class NewsImageServiceImpl extends BaseServiceImpl<NewsImageDTO, NewsImage> implements NewsImageService {

  private final MediaService mediaService;

  public NewsImageServiceImpl(ApplicationEventPublisher publisher, NewsImageRepository newsImageRepository,
      MediaService mediaService) {
    super(newsImageRepository, publisher);
    this.mediaService = mediaService;
  }

  @Override
  protected NewsImageDTO toDTO(NewsImage entity) {

    NewsImageDTO dto = new NewsImageDTO();
    fillObject(entity, dto);

    if (StringUtils.hasText(entity.getMediaId())) {
      MediaDTO media = mediaService.getEntity(Long.parseLong(entity.getMediaId()));
      dto.setMedia(media);
    }

    return dto;
  }

  @Override
  protected NewsImage toEntity(NewsImageDTO dto) {

    NewsImage entity = new NewsImage();
    fillObject(dto, entity);
    String mediaId = null;
    if (dto.getMedia() != null) {
      mediaId = String.valueOf(dto.getMedia().getId());
    }
    entity.setMediaId(mediaId);

    return entity;
  }

}
