package com.cmpl.web.core.page;

import com.cmpl.web.core.common.resource.BaseResponse;

public class PageResponse extends BaseResponse {

  private PageDTO page;

  public PageDTO getPage() {
    return page;
  }

  public void setPage(PageDTO page) {
    this.page = page;
  }

}
