package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;

public class ChangePasswordFormBuilder extends Builder<ChangePasswordForm> {

  private String password;
  private String confirmation;
  private String token;

  private ChangePasswordFormBuilder() {

  }

  public ChangePasswordFormBuilder password(String password) {
    this.password = password;
    return this;
  }

  public ChangePasswordFormBuilder confirmation(String confirmation) {
    this.confirmation = confirmation;
    return this;
  }

  public ChangePasswordFormBuilder token(String token) {
    this.token = token;
    return this;
  }

  @Override
  public ChangePasswordForm build() {
    ChangePasswordForm form = new ChangePasswordForm();
    form.setConfirmation(confirmation);
    form.setPassword(password);
    form.setToken(token);
    return form;
  }

  public static ChangePasswordFormBuilder create() {
    return new ChangePasswordFormBuilder();
  }
}
