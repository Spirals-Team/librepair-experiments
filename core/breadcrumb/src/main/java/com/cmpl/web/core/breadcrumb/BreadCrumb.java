package com.cmpl.web.core.breadcrumb;

import java.util.List;

import com.cmpl.web.core.page.BACK_PAGE;
 
public class BreadCrumb implements BreadCrumbPlugin {

  private List<BreadCrumbItem> items;
  private BACK_PAGE page;

  public List<BreadCrumbItem> getItems() {
    return items;
  }

  public BACK_PAGE getPage() {
    return page;
  }

  public void setPage(BACK_PAGE page) {
    this.page = page;
  }

  public void setItems(List<BreadCrumbItem> items) {
    this.items = items;
  }

  @Override
  public boolean supports(BACK_PAGE delimiter) {
    return page.equals(delimiter);
  }

}
