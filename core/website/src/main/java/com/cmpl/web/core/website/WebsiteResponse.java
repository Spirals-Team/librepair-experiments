package com.cmpl.web.core.website;

import com.cmpl.web.core.common.resource.BaseResponse;

public class WebsiteResponse extends BaseResponse {

  private WebsiteDTO website;

  public WebsiteDTO getWebsite() {
    return website;
  }

  public void setWebsite(WebsiteDTO website) {
    this.website = website;
  }
}
