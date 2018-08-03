package com.cmpl.web.core.carousel.item;

import javax.validation.constraints.NotBlank;

public class CarouselItemCreateForm {

  @NotBlank(message = "empty.carousel.id")
  private String carouselId;
  private int orderInCarousel;
  @NotBlank(message = "empty.media.id")
  private String mediaId;

  public String getCarouselId() {
    return carouselId;
  }

  public void setCarouselId(String carouselId) {
    this.carouselId = carouselId;
  }

  public int getOrderInCarousel() {
    return orderInCarousel;
  }

  public void setOrderInCarousel(int orderInCarousel) {
    this.orderInCarousel = orderInCarousel;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
