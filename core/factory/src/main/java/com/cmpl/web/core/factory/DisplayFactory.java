package com.cmpl.web.core.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

/**
 * Interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public interface DisplayFactory {

  /**
   * Creer le model and view correspondant a la page front du site
   * 
   * @param pageName
   * @param locale
   * @param pageNumber
   * @return
   */
  ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForAMP(String pageName, Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForBlogEntry(String blogEntryId, String widgetId, Locale locale);

  ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber, String pageId);

}
