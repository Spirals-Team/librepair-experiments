package com.cmpl.web.core.design;

import com.cmpl.web.core.common.dto.BaseDTO;

public class DesignDTO extends BaseDTO {

  private Long websiteId;
  private Long styleId;

  public Long getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(Long websiteId) {
    this.websiteId = websiteId;
  }

  public Long getStyleId() {
    return styleId;
  }

  public void setStyleId(Long styleId) {
    this.styleId = styleId;
  }
}
