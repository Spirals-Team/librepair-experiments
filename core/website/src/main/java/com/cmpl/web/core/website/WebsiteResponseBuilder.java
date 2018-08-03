package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.Builder;

public class WebsiteResponseBuilder extends Builder<WebsiteResponse> {

  private WebsiteDTO website;

  public WebsiteResponseBuilder website(WebsiteDTO website) {
    this.website = website;
    return this;
  }

  private WebsiteResponseBuilder() {

  }

  @Override
  public WebsiteResponse build() {
    WebsiteResponse response = new WebsiteResponse();
    response.setWebsite(website);

    return response;
  }

  public static WebsiteResponseBuilder create() {
    return new WebsiteResponseBuilder();
  }
}
