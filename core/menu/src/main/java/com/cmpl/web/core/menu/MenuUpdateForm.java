package com.cmpl.web.core.menu;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class MenuUpdateForm extends BaseUpdateForm<MenuDTO> {

  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  public MenuUpdateForm() {

  }

  public MenuUpdateForm(MenuDTO menuDTO) {
    super(menuDTO);
    this.title = menuDTO.getTitle();
    this.href = menuDTO.getHref();
    this.label = menuDTO.getLabel();
    this.orderInMenu = menuDTO.getOrderInMenu();
    this.pageId = menuDTO.getPageId();
    this.parentId = menuDTO.getParentId();
  }

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

  public int getOrderInMenu() {
    return orderInMenu;
  }

  public void setOrderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

}
