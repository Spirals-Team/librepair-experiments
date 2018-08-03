package com.cmpl.web.facebook;

import java.util.List;

import com.cmpl.web.core.common.exception.BaseException;

/**
 * Interface pour recuperer les posts facebook d'un utilisateur
 * 
 * @author Louis
 *
 */
public interface FacebookService {

  /**
   * Recuperation des 25 derniers posts de l'utilisateur
   * 
   * @return
   * @throws BaseException
   */
  List<ImportablePost> getRecentFeed() throws BaseException;

}
