package com.cmpl.web.core.carousel;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface CarouselItemService extends BaseService<CarouselItemDTO> {

  List<CarouselItemDTO> getByCarouselId(String carouselId);

  void deleteEntityInCarousel(String carouselId, Long id);

}
