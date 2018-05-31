package com.cmpl.web.core.user;

public class ChangePasswordForm {

  private String password;
  private String confirmation;
  private String token;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmation() {
    return confirmation;
  }

  public void setConfirmation(String confirmation) {
    this.confirmation = confirmation;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
