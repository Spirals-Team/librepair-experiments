package com.cmpl.web.modules.facebook.factory;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

/**
 * Interface de factory pour le spages back de facebook
 * 
 * @author Louis
 *
 */
public interface FacebookDisplayFactory {

  /**
   * Creer le model and vie wpour la page d'acces a facebook
   * 
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale);

  /**
   * Creer le model and view pour la page d'import de facebook
   * 
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForFacebookImportPage(Locale locale);
}
