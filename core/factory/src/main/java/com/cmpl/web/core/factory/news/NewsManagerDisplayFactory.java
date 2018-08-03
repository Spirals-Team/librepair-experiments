package com.cmpl.web.core.factory.news;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

/**
 * Interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public interface NewsManagerDisplayFactory extends BackDisplayFactory {

  /**
   * Creer le model and view pour l'edition d'une NewsEntry
   * 
   * @param backPage
   * @param locale
   * @param newsEntryId
   * @return
   */
  ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId);

  /**
   * Creer le model and view pour l'affichage de toutes les NewsEntry modifiables
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForBackPage(Locale locale, int pageNumber);

  /**
   * Creer le model and view pour l'affichage de la creation d'une entree
   * 
   * @param backPage
   * @param locale
   * @return
   */
  ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsMain(String newsEntryId, Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsContent(String newsEntryId, Locale locale);

  ModelAndView computeModelAndViewForUpdateNewsImage(String newsEntryId, Locale locale);

}
