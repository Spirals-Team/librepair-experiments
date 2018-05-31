package com.cmpl.web.core.carousel;

import com.cmpl.web.core.common.builder.Builder;

public class CarouselItemCreateFormBuilder extends Builder<CarouselItemCreateForm> {

  private String carouselId;
  private int orderInCarousel;
  private String mediaId;

  private CarouselItemCreateFormBuilder() {

  }

  public CarouselItemCreateFormBuilder carouselId(String carouselId) {
    this.carouselId = carouselId;
    return this;
  }

  public CarouselItemCreateFormBuilder orderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
    return this;
  }

  public CarouselItemCreateFormBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  @Override
  public CarouselItemCreateForm build() {
    CarouselItemCreateForm form = new CarouselItemCreateForm();
    form.setCarouselId(carouselId);
    form.setMediaId(mediaId);
    form.setOrderInCarousel(orderInCarousel);
    return form;
  }

  public static CarouselItemCreateFormBuilder create() {
    return new CarouselItemCreateFormBuilder();
  }

}
