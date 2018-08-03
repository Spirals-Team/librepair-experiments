package com.cmpl.web.core.menu;

import java.util.List;

import com.cmpl.web.core.common.builder.Builder;

public class MenuItemBuilder extends Builder<MenuItem> {

  private String href;
  private String label;
  private String title;
  private List<MenuItem> subMenuItems;
  private String customCssClass;
  private String iconClass;
  private String privilege;

  private MenuItemBuilder() {
  }

  public MenuItemBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuItemBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuItemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuItemBuilder subMenuItems(List<MenuItem> subMenuItems) {
    this.subMenuItems = subMenuItems;
    return this;
  }

  public MenuItemBuilder customCssClass(String customCssClass) {
    this.customCssClass = customCssClass;
    return this;
  }

  public MenuItemBuilder iconClass(String iconClass) {
    this.iconClass = iconClass;
    return this;
  }

  public MenuItemBuilder privilege(String privilege) {
    this.privilege = privilege;
    return this;
  }

  @Override
  public MenuItem build() {
    MenuItem menuItem = new MenuItem();
    menuItem.setHref(href);
    menuItem.setLabel(label);
    menuItem.setTitle(title);
    menuItem.setSubMenuItems(subMenuItems);
    menuItem.setCustomCssClass(customCssClass);
    menuItem.setIconClass(iconClass);
    menuItem.setPrivilege(privilege);

    return menuItem;
  }

  public static MenuItemBuilder create() {
    return new MenuItemBuilder();
  }

}
