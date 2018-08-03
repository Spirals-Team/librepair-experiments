package com.cmpl.web.core.carousel;

import javax.validation.constraints.NotBlank;

public class CarouselCreateForm {

  @NotBlank(message = "empty.carousel.name")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
