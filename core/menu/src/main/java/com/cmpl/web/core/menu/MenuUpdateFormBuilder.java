package com.cmpl.web.core.menu;

import java.time.LocalDateTime;

import com.cmpl.web.core.common.builder.Builder;

public class MenuUpdateFormBuilder extends Builder<MenuUpdateForm> {

  private Long id;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String creationUser;
  private String modificationUser;
  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;

  private MenuUpdateFormBuilder() {
  }

  public MenuUpdateFormBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuUpdateFormBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuUpdateFormBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuUpdateFormBuilder orderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
    return this;
  }

  public MenuUpdateFormBuilder parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  public MenuUpdateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public MenuUpdateFormBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public MenuUpdateFormBuilder creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  public MenuUpdateFormBuilder modificationDate(LocalDateTime modificationDate) {
    this.modificationDate = modificationDate;
    return this;
  }

  public MenuUpdateFormBuilder creationUser(String creationUser) {
    this.creationUser = creationUser;
    return this;
  }

  public MenuUpdateFormBuilder modificationUser(String modificationUser) {
    this.modificationUser = modificationUser;
    return this;
  }

  @Override
  public MenuUpdateForm build() {
    MenuUpdateForm form = new MenuUpdateForm();
    form.setCreationDate(creationDate);
    form.setHref(href);
    form.setId(id);
    form.setLabel(label);
    form.setModificationDate(modificationDate);
    form.setOrderInMenu(orderInMenu);
    form.setPageId(pageId);
    form.setParentId(parentId);
    form.setTitle(title);
    form.setCreationUser(creationUser);
    form.setModificationUser(modificationUser);
    return form;
  }

  public static MenuUpdateFormBuilder create() {
    return new MenuUpdateFormBuilder();
  }

}
