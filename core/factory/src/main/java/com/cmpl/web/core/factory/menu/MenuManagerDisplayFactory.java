package com.cmpl.web.core.factory.menu;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

public interface MenuManagerDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMenus(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateMenu(Locale locale);

  ModelAndView computeModelAndViewForUpdateMenu(Locale locale, String menuId);

}
