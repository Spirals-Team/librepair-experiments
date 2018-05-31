package com.cmpl.web.core.page;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class PageResponseBuilder extends Builder<PageResponse> {

  private PageDTO page;
  private Error error;

  private PageResponseBuilder() {

  }

  public PageResponseBuilder page(PageDTO page) {
    this.page = page;
    return this;
  }

  public PageResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public PageResponse build() {
    PageResponse response = new PageResponse();
    response.setPage(page);
    response.setError(error);
    return response;
  }

  public static PageResponseBuilder create() {
    return new PageResponseBuilder();
  }

}
