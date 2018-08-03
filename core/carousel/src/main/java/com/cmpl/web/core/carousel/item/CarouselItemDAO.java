package com.cmpl.web.core.carousel.item;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.CarouselItem;

public interface CarouselItemDAO extends BaseDAO<CarouselItem> {

  List<CarouselItem> getByCarouselId(String carouselId);

}
