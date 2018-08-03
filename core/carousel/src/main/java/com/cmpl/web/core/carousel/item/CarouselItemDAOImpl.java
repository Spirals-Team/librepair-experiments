package com.cmpl.web.core.carousel.item;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.CarouselItem;

public class CarouselItemDAOImpl extends BaseDAOImpl<CarouselItem> implements CarouselItemDAO {

  private final CarouselItemRepository carouselItemRepository;

  public CarouselItemDAOImpl(CarouselItemRepository entityRepository, ApplicationEventPublisher publisher) {
    super(CarouselItem.class, entityRepository, publisher);
    this.carouselItemRepository = entityRepository;
  }

  @Override
  public List<CarouselItem> getByCarouselId(String carouselId) {
    return carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(carouselId);
  }
}
