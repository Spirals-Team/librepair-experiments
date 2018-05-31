package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class CarouselItemResponseBuilder extends Builder<CarouselItemResponse> {

  private CarouselItemDTO carouselItem;
  private Error error;

  private CarouselItemResponseBuilder() {

  }

  public CarouselItemResponseBuilder item(CarouselItemDTO carouselItem) {
    this.carouselItem = carouselItem;
    return this;
  }

  public CarouselItemResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public CarouselItemResponse build() {
    CarouselItemResponse response = new CarouselItemResponse();
    response.setItem(carouselItem);
    response.setError(error);
    return response;
  }

  public static CarouselItemResponseBuilder create() {
    return new CarouselItemResponseBuilder();
  }
}
