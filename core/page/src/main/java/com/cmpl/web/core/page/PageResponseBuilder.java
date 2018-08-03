package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.Builder;

public class PageResponseBuilder extends Builder<PageResponse> {

  private PageDTO page;

  private PageResponseBuilder() {

  }

  public PageResponseBuilder page(PageDTO page) {
    this.page = page;
    return this;
  }

  @Override
  public PageResponse build() {
    PageResponse response = new PageResponse();
    response.setPage(page);

    return response;
  }

  public static PageResponseBuilder create() {
    return new PageResponseBuilder();
  }

}
