package com.cmpl.web.core.menu;

import java.util.List;

import com.cmpl.web.core.common.dto.BaseDTO;

/**
 * DTO Menu
 * 
 * @author Louis
 *
 */
public class MenuDTO extends BaseDTO {

  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;
  private List<MenuDTO> children;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public List<MenuDTO> getChildren() {
    return children;
  }

  public void setChildren(List<MenuDTO> children) {
    this.children = children;
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

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

}
