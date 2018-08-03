package com.cmpl.web.core.breadcrumb;


import com.cmpl.web.core.common.builder.Builder;

public class BreadCrumbItemBuilder extends Builder<BreadCrumbItem> {

  private String text;
  private String href;

  private BreadCrumbItemBuilder() {

  }

  public BreadCrumbItemBuilder text(String text) {
    this.text = text;
    return this;
  }

  public BreadCrumbItemBuilder href(String href) {
    this.href = href;
    return this;
  }

  @Override
  public BreadCrumbItem build() {
    BreadCrumbItem item = new BreadCrumbItem();
    item.setHref(href);
    item.setText(text);
    return item;
  }

  public static BreadCrumbItemBuilder create() {
    return new BreadCrumbItemBuilder();
  }

}
