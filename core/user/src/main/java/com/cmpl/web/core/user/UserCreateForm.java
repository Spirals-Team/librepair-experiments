package com.cmpl.web.core.user;

import javax.validation.constraints.NotBlank;

public class UserCreateForm {

  @NotBlank(message = "empty.user.login")
  private String login;
  @NotBlank(message = "empty.user.email")
  private String email;
  private String description;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
