package com.cmpl.web.core.page;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class PageUpdateForm extends BaseUpdateForm<PageDTO> {

  private String name;
  private String menuTitle;
  private String body;
  private String header;
  private String footer;
  private String meta;
  private String amp;
  private String localeCode;

  public PageUpdateForm() {

  }

  public PageUpdateForm(PageDTO page, String personalizationLanguageCode) {
    super(page);
    this.name = page.getName();
    this.menuTitle = page.getMenuTitle();
    this.body = page.getBody();
    this.footer = page.getFooter();
    this.header = page.getHeader();
    this.localeCode = personalizationLanguageCode;
    this.meta = page.getMeta();
    this.amp = page.getAmp();
  }

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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

  public String getLocaleCode() {
    return localeCode;
  }

  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
  }

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }

  public String getAmp() {
    return amp;
  }

  public void setAmp(String amp) {
    this.amp = amp;
  }
}
