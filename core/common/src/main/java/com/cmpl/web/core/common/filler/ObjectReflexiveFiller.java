package com.cmpl.web.core.common.filler;

/**
 * Classe permettant de copier le contenu d'un objet dans un autre pour les proprietes similaires
 * 
 * @author Louis
 *
 */
public interface ObjectReflexiveFiller {

  /**
   * Permet de remplir la destination avec les objets similaires de l'origine
   */
  void fillDestination();

}
