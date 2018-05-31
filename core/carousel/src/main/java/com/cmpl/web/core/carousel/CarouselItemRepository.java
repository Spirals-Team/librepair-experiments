package com.cmpl.web.core.carousel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface CarouselItemRepository extends BaseRepository<CarouselItem> {

  List<CarouselItem> findByCarouselIdOrderByOrderInCarousel(String carouselId);

}
