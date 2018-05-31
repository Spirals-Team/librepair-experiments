package com.cmpl.web.core.factory;

import java.util.Locale;

/**
 * Interface commune aux factory utilisant des cles i18n
 * 
 * @author Louis
 *
 */
public interface BaseFactory {

  /**
   * Recupere une valeur i18n en fonction de la locale et de la cle
   * 
   * @param key
   * @param locale
   * @return
   */
  String getI18nValue(String key, Locale locale);

  /**
   * Recupere une valeur i18n en fonction de la locale et de la cle et de parametres
   * 
   * @param key
   * @param locale
   * @return
   */
  String getI18nValue(String key, Locale locale, Object... args);

}
