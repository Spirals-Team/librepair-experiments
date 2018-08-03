package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselItemResponseBuilder extends Builder<CarouselItemResponse> {

  private CarouselItemDTO carouselItem;

  private CarouselItemResponseBuilder() {

  }

  public CarouselItemResponseBuilder item(CarouselItemDTO carouselItem) {
    this.carouselItem = carouselItem;
    return this;
  }

  @Override
  public CarouselItemResponse build() {
    CarouselItemResponse response = new CarouselItemResponse();
    response.setItem(carouselItem);
    return response;
  }

  public static CarouselItemResponseBuilder create() {
    return new CarouselItemResponseBuilder();
  }
}
