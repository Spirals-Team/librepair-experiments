package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class PageBuilder extends BaseBuilder<Page> {

  private String name;
  private String menuTitle;

  private PageBuilder() {

  }

  public PageBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  @Override
  public Page build() {
    Page page = new Page();
    page.setCreationDate(creationDate);
    page.setId(id);
    page.setMenuTitle(menuTitle);
    page.setModificationDate(modificationDate);
    page.setName(name);
    return page;
  }

  public static PageBuilder create() {
    return new PageBuilder();
  }

}
