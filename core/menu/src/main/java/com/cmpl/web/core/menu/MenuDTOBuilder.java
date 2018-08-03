package com.cmpl.web.core.menu;

import java.util.List;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class MenuDTOBuilder extends BaseBuilder<MenuDTO> {

  private String title;
  private String label;
  private String href;
  private int orderInMenu;
  private String parentId;
  private String pageId;
  private List<MenuDTO> children;

  private MenuDTOBuilder() {

  }

  public MenuDTOBuilder title(String title) {
    this.title = title;
    return this;
  }

  public MenuDTOBuilder label(String label) {
    this.label = label;
    return this;
  }

  public MenuDTOBuilder href(String href) {
    this.href = href;
    return this;
  }

  public MenuDTOBuilder orderInMenu(int orderInMenu) {
    this.orderInMenu = orderInMenu;
    return this;
  }

  public MenuDTOBuilder parentId(String parentId) {
    this.parentId = parentId;
    return this;
  }

  public MenuDTOBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public MenuDTOBuilder children(List<MenuDTO> children) {
    this.children = children;
    return this;
  }

  @Override
  public MenuDTO build() {
    MenuDTO menuDTO = new MenuDTO();
    menuDTO.setChildren(children);
    menuDTO.setCreationDate(creationDate);
    menuDTO.setHref(href);
    menuDTO.setId(id);
    menuDTO.setLabel(label);
    menuDTO.setModificationDate(modificationDate);
    menuDTO.setOrderInMenu(orderInMenu);
    menuDTO.setPageId(pageId);
    menuDTO.setParentId(parentId);
    menuDTO.setTitle(title);
    return menuDTO;
  }

  public static MenuDTOBuilder create() {
    return new MenuDTOBuilder();
  }

}
