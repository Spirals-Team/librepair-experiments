package com.cmpl.web.core.carousel.item;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.CarouselItem;

@Repository
public interface CarouselItemRepository extends BaseRepository<CarouselItem> {

  List<CarouselItem> findByCarouselIdOrderByOrderInCarousel(String carouselId);

}
