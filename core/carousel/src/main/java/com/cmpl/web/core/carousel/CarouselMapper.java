package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Carousel;

public class CarouselMapper extends BaseMapper<CarouselDTO, Carousel> {

  private final CarouselItemService carouselItemService;

  public CarouselMapper(CarouselItemService carouselItemService) {
    this.carouselItemService = carouselItemService;
  }

  @Override
  public CarouselDTO toDTO(Carousel entity) {
    CarouselDTO dto = CarouselDTOBuilder.create()
        .carouselItems(carouselItemService.getByCarouselId(String.valueOf(entity.getId()))).build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Carousel toEntity(CarouselDTO dto) {
    Carousel carousel = CarouselBuilder.create().build();
    fillObject(dto, carousel);
    return carousel;
  }
}
