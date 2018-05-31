package com.cmpl.web.core.carousel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * Object pour les carousel d'image
 * 
 * @author Louis
 *
 */
@Entity(name = "carouselItem")
@Table(name = "carouse_item")
public class CarouselItem extends BaseEntity {
 
  @Column(name = "media_id")
  private String mediaId;
  @Column(name = "carousel_id")
  private String carouselId;
  @Column(name = "order_in_carousel")
  private int orderInCarousel;

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
