package com.cmpl.web.core.common.user;

import com.cmpl.web.core.common.builder.Builder;

public class BackUserBuilder extends Builder<BackUser> {

  private String login;
  private String password;

  private BackUserBuilder(){

  }

  public BackUserBuilder login(String login) {
    this.login = login;
    return this;
  }

  public BackUserBuilder password(String password) {
    this.password = password;
    return this;
  }

  @Override
  public BackUser build() {
    BackUser backUser = new BackUser();
    backUser.setLogin(login);
    backUser.setPassword(password);
    return backUser;
  }

  public static BackUserBuilder create() {
    return new BackUserBuilder();
  }

}
