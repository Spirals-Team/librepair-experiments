package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselCreateFormBuilder extends Builder<CarouselCreateForm> {

  private String name;

  private CarouselCreateFormBuilder() {

  }

  public CarouselCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public CarouselCreateForm build() {
    CarouselCreateForm form = new CarouselCreateForm();
    form.setName(name);
    return form;
  }

  public static CarouselCreateFormBuilder create() {
    return new CarouselCreateFormBuilder();
  }

}
