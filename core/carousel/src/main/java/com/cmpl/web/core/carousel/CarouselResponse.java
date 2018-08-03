package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.resource.BaseResponse;

public class CarouselResponse extends BaseResponse {

  private CarouselDTO carousel;

  public CarouselDTO getCarousel() {
    return carousel;
  }

  public void setCarousel(CarouselDTO carousel) {
    this.carousel = carousel;
  }

}
