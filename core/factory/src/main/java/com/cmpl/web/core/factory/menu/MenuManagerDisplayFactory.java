package com.cmpl.web.core.factory.menu;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface MenuManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllMenus(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateMenu(Locale locale);

  ModelAndView computeModelAndViewForUpdateMenuMain(String menuId);

  ModelAndView computeModelAndViewForUpdateMenu(Locale locale, String menuId);

}
