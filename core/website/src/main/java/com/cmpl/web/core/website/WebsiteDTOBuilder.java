package com.cmpl.web.core.website;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WebsiteDTOBuilder extends BaseBuilder<WebsiteDTO> {

  private String name;

  private String description;

  private boolean secure;

  private WebsiteDTOBuilder() {

  }

  public WebsiteDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WebsiteDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public WebsiteDTOBuilder secure(boolean secure) {
    this.secure = secure;
    return this;
  }

  @Override
  public WebsiteDTO build() {
    WebsiteDTO website = new WebsiteDTO();
    website.setDescription(description);
    website.setName(name);
    website.setSecure(secure);
    return website;
  }

  public static WebsiteDTOBuilder create() {
    return new WebsiteDTOBuilder();
  }
}
