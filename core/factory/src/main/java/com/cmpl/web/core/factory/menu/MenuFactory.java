package com.cmpl.web.core.factory.menu;

import java.util.List;
import java.util.Locale;

import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageDTO;

/**
 * Interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public interface MenuFactory {

  /**
   * Creer le menu front pour la page du front
   * 
   * @param page
   * @param locale
   * @return
   */
  List<MenuItem> computeMenuItems(PageDTO page, Locale locale);

  /**
   * Creer le menu back pour la page du back
   * 
   * @param backPage
   * @param locale
   * @return
   */
  List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale);

}
