package com.cmpl.web.core.breadcrumb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.page.BACK_PAGE;

public class BreadCrumbBuilder extends Builder<BreadCrumb> {

  private List<BreadCrumbItem> items;
  private BACK_PAGE page;

  private BreadCrumbBuilder() {
    items = new ArrayList<>();
  }

  public BreadCrumbBuilder items(List<BreadCrumbItem> items) {
    this.items = items;
    return this;
  }

  public BreadCrumbBuilder page(BACK_PAGE page) {
    this.page = page;
    return this;
  }

  @Override
  public BreadCrumb build() {
    BreadCrumb breadCrumb = new BreadCrumb();
    breadCrumb.setItems(items.stream().collect(Collectors.toList()));
    breadCrumb.setPage(page);
    return breadCrumb;
  }

  public static BreadCrumbBuilder create() {
    return new BreadCrumbBuilder();
  }

}
