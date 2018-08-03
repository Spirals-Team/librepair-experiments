package com.cmpl.web.core.sitemap.rendering;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

/**
 * Interface gerant le sitemap
 * 
 * @author Louis
 *
 */
public interface RenderingSitemapService {

  /**
   * Creer un sitemap et renvoyer le contenu dans un String
   * 
   * @param locale
   * @return
   * @throws BaseException
   */
  String createSiteMap(String websiteName, Locale locale) throws BaseException;

}
