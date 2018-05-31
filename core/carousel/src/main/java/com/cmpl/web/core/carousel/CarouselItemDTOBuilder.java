package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.media.MediaDTO;

public class CarouselItemDTOBuilder extends BaseBuilder<CarouselItemDTO> {

  private MediaDTO media;
  private String carouselId;
  private int orderInCarousel;

  private CarouselItemDTOBuilder() {

  }

  public CarouselItemDTOBuilder media(MediaDTO media) {
    this.media = media;
    return this;
  }

  public CarouselItemDTOBuilder carouselId(String carouselId) {
    this.carouselId = carouselId;
    return this;
  }

  public CarouselItemDTOBuilder orderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
    return this;
  }

  @Override
  public CarouselItemDTO build() {
    CarouselItemDTO dto = new CarouselItemDTO();
    dto.setCarouselId(carouselId);
    dto.setCreationDate(creationDate);
    dto.setId(id);
    dto.setMedia(media);
    dto.setModificationDate(modificationDate);
    dto.setOrderInCarousel(orderInCarousel);
    return dto;
  }

  public static CarouselItemDTOBuilder create() {
    return new CarouselItemDTOBuilder();
  }

}
