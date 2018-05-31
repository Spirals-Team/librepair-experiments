package com.cmpl.web.core.factory.style;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface StyleDisplayFactory {

  ModelAndView computeModelAndViewForViewStyles(Locale locale);

  ModelAndView computeModelAndViewForUpdateStyles(Locale locale);

}
