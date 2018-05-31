package com.cmpl.web.core.carousel;

import java.util.List;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class CarouselDTOBuilder extends BaseBuilder<CarouselDTO> {

  private String name;
  private List<CarouselItemDTO> carouselItems;

  private CarouselDTOBuilder() {

  }

  public CarouselDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CarouselDTOBuilder carouselItems(List<CarouselItemDTO> carouselItems) {
    this.carouselItems = carouselItems;
    return this;
  }

  @Override
  public CarouselDTO build() {
    CarouselDTO dto = new CarouselDTO();
    dto.setCarouselItems(carouselItems);
    dto.setCreationDate(creationDate);
    dto.setId(id);
    dto.setModificationDate(modificationDate);
    dto.setName(name);
    return dto;
  }

  public static CarouselDTOBuilder create() {
    return new CarouselDTOBuilder();
  }

}
