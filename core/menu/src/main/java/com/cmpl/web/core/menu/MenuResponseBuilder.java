package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.builder.Builder;

public class MenuResponseBuilder extends Builder<MenuResponse> {

  private MenuDTO menu;

  private MenuResponseBuilder() {
  }

  public MenuResponseBuilder menu(MenuDTO menu) {
    this.menu = menu;
    return this;
  }

  @Override
  public MenuResponse build() {
    MenuResponse response = new MenuResponse();
    response.setMenu(menu);

    return response;
  }

  public static MenuResponseBuilder create() {
    return new MenuResponseBuilder();
  }

}
