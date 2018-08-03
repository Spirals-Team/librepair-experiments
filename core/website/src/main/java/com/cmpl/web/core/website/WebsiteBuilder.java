package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Website;

public class WebsiteBuilder extends BaseBuilder<Website> {

  private String name;

  private String description;

  private boolean secure;

  private WebsiteBuilder() {

  }

  public WebsiteBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  @Override
  public Website build() {
    Website website = new Website();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    return website;
  }

  public static WebsiteBuilder create() {
    return new WebsiteBuilder();
  }
}
