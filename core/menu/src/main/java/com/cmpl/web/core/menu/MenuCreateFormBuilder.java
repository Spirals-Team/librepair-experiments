package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.builder.Builder;

public class MenuCreateFormBuilder extends Builder<MenuCreateForm> {

  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  private MenuCreateFormBuilder() {

  }

  public MenuCreateFormBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuCreateFormBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuCreateFormBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuCreateFormBuilder orderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
    return this;
  }

  public MenuCreateFormBuilder parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  public MenuCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public MenuCreateForm build() {
    MenuCreateForm form = new MenuCreateForm();
    form.setHref(href);
    form.setLabel(label);
    form.setOrderInMenu(orderInMenu);
    form.setPageId(pageId);
    form.setParentId(parentId);
    form.setTitle(title);
    return form;
  }

  public static MenuCreateFormBuilder create() {
    return new MenuCreateFormBuilder();
  }

}
