package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.Builder;

public class PageCreateFormBuilder extends Builder<PageCreateForm> {

  private String name = "";
  private String menuTitle = "";
  private String body = "";
  private String header = "";
  private String footer = "";
  private String meta = "";
  private String localeCode;

  private PageCreateFormBuilder() {

  }

  public PageCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public PageCreateFormBuilder menuTitle(String menuTitle) {
    this.menuTitle = menuTitle;
    return this;
  }

  public PageCreateFormBuilder body(String body) {
    this.body = body;
    return this;
  }

  public PageCreateFormBuilder header(String header) {
    this.header = header;
    return this;
  }

  public PageCreateFormBuilder footer(String footer) {
    this.footer = footer;
    return this;
  }

  public PageCreateFormBuilder meta(String meta) {
    this.meta = meta;
    return this;
  }

  public PageCreateFormBuilder localeCode(String localeCode) {
    this.localeCode = localeCode;
    return this;
  }

  @Override
  public PageCreateForm build() {
    PageCreateForm form = new PageCreateForm();
    form.setBody(body);
    form.setFooter(footer);
    form.setHeader(header);
    form.setMeta(meta);
    form.setMenuTitle(menuTitle);
    form.setName(name);
    form.setLocaleCode(localeCode);
    return form;
  }

  public static PageCreateFormBuilder create() {
    return new PageCreateFormBuilder();
  }

}
