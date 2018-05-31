package com.cmpl.web.core.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * DAO Menu
 * 
 * @author Louis
 *
 */
@Entity(name = "menu")
@Table(name = "menu")
public class Menu extends BaseEntity {

  @Column(name = "title")
  private String title;
  @Column(name = "label")
  private String label;
  @Column(name = "href")
  private String href;
  @Column(name = "order_in_menu")
  private int orderInMenu;
  @Column(name = "parent_id")
  private String parentId;
  @Column(name = "page_id")
  private String pageId;

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
