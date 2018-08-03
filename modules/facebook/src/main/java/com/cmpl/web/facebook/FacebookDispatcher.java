package com.cmpl.web.facebook;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

/**
 * Dispatcher pour le controller facebook
 * 
 * @author Louis
 *
 */
public interface FacebookDispatcher {

  /**
   * Permet la creation d'une entite a partir d'un post facebook
   * 
   * @param facebookImportRequest
   * @param locale
   * @return
   * @throws BaseException
   */
  FacebookImportResponse createEntity(FacebookImportRequest facebookImportRequest, Locale locale) throws BaseException;

}
