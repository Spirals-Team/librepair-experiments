package com.cmpl.web.core.factory;

import java.util.Locale;

/**
 * Interface commune de factory pour les pages
 * 
 * @author Louis
 *
 */
public interface BaseDisplayFactory extends BaseFactory {

  /**
   * Recupere le lien pour le back office
   * 
   * @param locale
   * @return
   */
  String computeHiddenLink(Locale locale);
}
