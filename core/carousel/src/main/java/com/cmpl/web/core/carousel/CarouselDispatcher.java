package com.cmpl.web.core.carousel;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface CarouselDispatcher {

  CarouselResponse createEntity(CarouselCreateForm form, Locale locale);

  CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale);

  CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale);

  CarouselResponse deleteEntity(String carouselId, Locale locale);

  void deleteCarouselItemEntity(String carouselId, String carouselItemId, Locale locale) throws BaseException;

}
