package com.cmpl.web.core.common.error;

import java.util.List;

/**
 * Classe d'erreur contenant des causes
 * 
 * @author Louis
 *
 */
public class Error extends ErrorCause {

  private List<ErrorCause> causes;

  public List<ErrorCause> getCauses() {
    return causes;
  }

  public void setCauses(List<ErrorCause> causes) {
    this.causes = causes;
  }

}
