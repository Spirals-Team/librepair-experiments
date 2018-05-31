package com.cmpl.web.core.sitemap;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

/**
 * Interface gerant le sitemap
 * 
 * @author Louis
 *
 */
public interface SitemapService {

  /**
   * Creer un sitemap et renvoyer le contenu dans un String
   * 
   * @param locale
   * @return
   * @throws BaseException
   */
  String createSiteMap(Locale locale) throws BaseException;

}
