package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselResponseBuilder extends Builder<CarouselResponse> {

  private CarouselDTO carousel;

  private CarouselResponseBuilder() {

  }

  public CarouselResponseBuilder carousel(CarouselDTO carousel) {
    this.carousel = carousel;
    return this;
  }

  @Override
  public CarouselResponse build() {
    CarouselResponse response = new CarouselResponse();
    response.setCarousel(carousel);

    return response;
  }

  public static CarouselResponseBuilder create() {
    return new CarouselResponseBuilder();
  }

}
