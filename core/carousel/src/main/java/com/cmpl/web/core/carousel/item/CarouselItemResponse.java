package com.cmpl.web.core.carousel.item;

import com.cmpl.web.core.common.resource.BaseResponse;

public class CarouselItemResponse extends BaseResponse {

  private CarouselItemDTO item;

  public CarouselItemDTO getItem() {
    return item;
  }

  public void setItem(CarouselItemDTO item) {
    this.item = item;
  }

}
