package com.cmpl.web.core.design;

import javax.validation.constraints.NotBlank;

public class DesignCreateForm {

  @NotBlank(message = "empty.website.id")
  private String websiteId;

  @NotBlank(message = "empty.style.id")
  private String styleId;

  public String getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(String websiteId) {
    this.websiteId = websiteId;
  }

  public String getStyleId() {
    return styleId;
  }

  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }
}
