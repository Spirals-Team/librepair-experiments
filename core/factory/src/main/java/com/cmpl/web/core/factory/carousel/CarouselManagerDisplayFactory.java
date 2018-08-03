package com.cmpl.web.core.factory.carousel;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface CarouselManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllCarousels(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForUpdateCarousel(Locale locale, String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselMain(String carouselId);

  ModelAndView computeModelAndViewForUpdateCarouselItems(String carouselId);

  ModelAndView computeModelAndViewForCreateCarousel(Locale locale);

}
