package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.builder.Builder;

public class BackMenuItemBuilder extends Builder<BackMenuItem> {

  private String title;
  private String label;
  private String href;
  private int order;
  private String iconClass;
  private BackMenuItem parent;
  private String privilege;

  private BackMenuItemBuilder() {

  }

  public BackMenuItemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public BackMenuItemBuilder label(String label) {
    this.label = label;
    return this;
  }

  public BackMenuItemBuilder href(String href) {
    this.href = href;
    return this;
  }

  public BackMenuItemBuilder order(int order) {
    this.order = order;
    return this;
  }

  public BackMenuItemBuilder iconClass(String iconClass) {
    this.iconClass = iconClass;
    return this;
  }

  public BackMenuItemBuilder parent(BackMenuItem parent) {
    this.parent = parent;
    return this;
  }

  public BackMenuItemBuilder privilege(String privilege) {
    this.privilege = privilege;
    return this;
  }

  @Override
  public BackMenuItem build() {
    BackMenuItem backMenuItem = new BackMenuItem();
    backMenuItem.setHref(href);
    backMenuItem.setLabel(label);
    backMenuItem.setTitle(title);
    backMenuItem.setOrder(order);
    backMenuItem.setIconClass(iconClass);
    backMenuItem.setParent(parent);
    backMenuItem.setPrivilege(privilege);
    return backMenuItem;
  }

  public static BackMenuItemBuilder create() {
    return new BackMenuItemBuilder();
  }

}
