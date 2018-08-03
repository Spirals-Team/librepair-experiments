package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.resource.BaseResponse;

public class MenuResponse extends BaseResponse {

  private MenuDTO menu;

  public MenuDTO getMenu() {
    return menu;
  }

  public void setMenu(MenuDTO menu) {
    this.menu = menu;
  }

}
