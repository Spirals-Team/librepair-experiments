package com.cmpl.web.core.carousel;

import java.util.List;

import com.cmpl.web.core.common.dto.BaseDTO;

public class CarouselDTO extends BaseDTO {

  private String name;
  private List<CarouselItemDTO> carouselItems;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CarouselItemDTO> getCarouselItems() {
    return carouselItems;
  }

  public void setCarouselItems(List<CarouselItemDTO> carouselItems) {
    this.carouselItems = carouselItems;
  }

}
