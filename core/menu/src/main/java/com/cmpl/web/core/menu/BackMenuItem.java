package com.cmpl.web.core.menu;

public class BackMenuItem implements BackMenuItemPlugin {

  private String title;
  private String label;
  private String href;
  private int order;
  private String iconClass;
  private BackMenuItem parent;
  private String privilege;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getIconClass() {
    return iconClass;
  }

  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }

  public String getPrivilege() {
    return privilege;
  }

  public void setPrivilege(String privilege) {
    this.privilege = privilege;
  }

  public BackMenuItem getParent() {
    return parent;
  }

  public void setParent(BackMenuItem parent) {
    this.parent = parent;
  }

  @Override
  public boolean supports(String delimiter) {
    return true;
  }

}
