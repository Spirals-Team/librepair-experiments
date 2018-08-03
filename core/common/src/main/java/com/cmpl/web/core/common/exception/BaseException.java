package com.cmpl.web.core.common.exception;

/**
 * Exception specifique au site
 * 
 * @author Louis
 *
 */
public class BaseException extends Exception {

  private static final long serialVersionUID = -7342218238341775717L;

  public BaseException() {
    // Constructeur vide pour certains cas d'utilisation
  }

  /**
   * Constructeur appelant le super de l'Exception
   * 
   * @param message
   */
  public BaseException(String message) {
    super(message);
  }

}
