package com.cmpl.web.core.carousel;

import javax.validation.constraints.NotBlank;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class CarouselUpdateForm extends BaseUpdateForm<CarouselDTO> {

  @NotBlank(message = "empty.carousel.name")
  private String name;

  public CarouselUpdateForm() {
  }

  public CarouselUpdateForm(CarouselDTO carousel) {
    super(carousel);
    this.name = carousel.getName();

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
