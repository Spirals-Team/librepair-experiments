package com.cmpl.web.core.common.error;

/**
 * Cause d'erreur
 * 
 * @author Louis
 *
 */
public class ErrorCause {

  private String code;
  private String message;
  private String faultyInput;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getFaultyInput() {
    return faultyInput;
  }

  public void setFaultyInput(String faultyInput) {
    this.faultyInput = faultyInput;
  }
}
