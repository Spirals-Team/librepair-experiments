package com.cmpl.web.core.factory.style;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface StyleDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllStyles(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateStyle(Locale locale);

  ModelAndView computeModelAndViewForUpdateStyle(Locale locale, String styleId);

  ModelAndView computeModelAndViewForUpdateStyleMain(Locale locale, String styleId);

}
