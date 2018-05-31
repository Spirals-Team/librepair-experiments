package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class MenuResponseBuilder extends Builder<MenuResponse> {

  private MenuDTO menu;
  private Error error;

  private MenuResponseBuilder() {
  }

  public MenuResponseBuilder menu(MenuDTO menu) {
    this.menu = menu;
    return this;
  }

  public MenuResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public MenuResponse build() {
    MenuResponse response = new MenuResponse();
    response.setMenu(menu);
    response.setError(error);
    return response;
  }

  public static MenuResponseBuilder create() {
    return new MenuResponseBuilder();
  }

}
