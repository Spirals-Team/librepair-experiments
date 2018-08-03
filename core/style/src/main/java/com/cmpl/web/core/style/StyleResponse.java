package com.cmpl.web.core.style;

import com.cmpl.web.core.common.resource.BaseResponse;

public class StyleResponse extends BaseResponse {

  private StyleDTO style;

  public StyleDTO getStyle() {
    return style;
  }

  public void setStyle(StyleDTO style) {
    this.style = style;
  }

}
