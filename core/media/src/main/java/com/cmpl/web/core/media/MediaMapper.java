package com.cmpl.web.core.media;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Media;

public class MediaMapper extends BaseMapper<MediaDTO, Media> {

  @Override
  public MediaDTO toDTO(Media entity) {
    if (entity == null) {
      return null;
    }
    MediaDTO dto = MediaDTOBuilder.create().build();

    fillObject(entity, dto);

    return dto;
  }

  @Override
  public Media toEntity(MediaDTO dto) {
    Media entity = MediaBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
