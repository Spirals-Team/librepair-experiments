package com.cmpl.web.core.page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

/**
 * DAO Page
 * 
 * @author Louis
 *
 */
@Entity(name = "page")
@Table(name = "page")
public class Page extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;
  @Column(name = "menu_title")
  private String menuTitle;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMenuTitle() {
    return menuTitle;
  }

  public void setMenuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
  }

}
