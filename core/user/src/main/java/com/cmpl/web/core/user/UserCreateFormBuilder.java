package com.cmpl.web.core.user;

import com.cmpl.web.core.common.builder.Builder;

public class UserCreateFormBuilder extends Builder<UserCreateForm> {

  private String login;
  private String email;
  private String description;

  private UserCreateFormBuilder() {

  }

  public UserCreateFormBuilder login(String login) {
    this.login = login;
    return this;
  }

  public UserCreateFormBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserCreateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  @Override
  public UserCreateForm build() {
    UserCreateForm form = new UserCreateForm();
    form.setDescription(description);
    form.setEmail(email);
    form.setLogin(login);
    return form;
  }

  public static UserCreateFormBuilder create() {
    return new UserCreateFormBuilder();
  }
}
