package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class MenuBuilder extends BaseBuilder<Menu> {

  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  private MenuBuilder() {

  }

  public MenuBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuBuilder orderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
    return this;
  }

  public MenuBuilder parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  public MenuBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  @Override
  public Menu build() {
    Menu menu = new Menu();
    menu.setCreationDate(creationDate);
    menu.setHref(href);
    menu.setId(id);
    menu.setLabel(label);
    menu.setModificationDate(modificationDate);
    menu.setOrderInMenu(orderInMenu);
    menu.setPageId(pageId);
    menu.setParentId(parentId);
    menu.setTitle(title);
    return menu;
  }

  public static MenuBuilder create() {
    return new MenuBuilder();
  }

}
