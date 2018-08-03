package com.cmpl.web.core.user;

import javax.validation.constraints.NotEmpty;

import com.cmpl.web.core.user.validation.PasswordConfirmation;
import com.cmpl.web.core.user.validation.PasswordDifferent;
import com.cmpl.web.core.user.validation.PasswordStrength;
import com.cmpl.web.core.user.validation.ValidToken;

@PasswordConfirmation
@PasswordDifferent
public class ChangePasswordForm {

  @NotEmpty(message = "empty.user.password")
  @PasswordStrength
  private String password;
  @NotEmpty(message = "empty.user.confirmation")
  private String confirmation;
  @NotEmpty(message = "token.invalid")
  @ValidToken
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

  @Override
  public String toString() {
    return "ChangePasswordForm{" + "password='" + password + '\'' + ", confirmation='" + confirmation + '\''
        + ", token='" + token + '\'' + '}';
  }
}
