package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class CarouselItemBuilder extends BaseBuilder<CarouselItem> {

  private String mediaId;
  private String carouselId;
  private int orderInCarousel;

  private CarouselItemBuilder() {

  }

  public CarouselItemBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  public CarouselItemBuilder carouselId(String carouselId) {
    this.carouselId = carouselId;
    return this;
  }

  public CarouselItemBuilder orderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
    return this;
  }

  @Override
  public CarouselItem build() {
    CarouselItem carouselItem = new CarouselItem();
    carouselItem.setCarouselId(carouselId);
    carouselItem.setCreationDate(creationDate);
    carouselItem.setId(id);
    carouselItem.setMediaId(mediaId);
    carouselItem.setModificationDate(modificationDate);
    carouselItem.setOrderInCarousel(orderInCarousel);
    return carouselItem;
  }

  public static CarouselItemBuilder create() {
    return new CarouselItemBuilder();
  }

}
