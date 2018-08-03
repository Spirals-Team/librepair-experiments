package com.cmpl.web.core.carousel.item;

import java.util.Objects;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.CarouselItem;

public class CarouselItemMapper extends BaseMapper<CarouselItemDTO, CarouselItem> {

  private final MediaService mediaService;

  public CarouselItemMapper(MediaService mediaService) {

    this.mediaService = Objects.requireNonNull(mediaService);

  }

  @Override
  public CarouselItemDTO toDTO(CarouselItem entity) {
    CarouselItemDTO dto = CarouselItemDTOBuilder.create()
        .media(mediaService.getEntity(Long.valueOf(entity.getMediaId()))).build();
    fillObject(entity, dto);

    return dto;
  }

  @Override
  public CarouselItem toEntity(CarouselItemDTO dto) {
    CarouselItem entity = CarouselItemBuilder.create().mediaId(String.valueOf(dto.getMedia().getId())).build();

    fillObject(entity, dto);

    return entity;
  }
}
