package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.Builder;

public class StyleResponseBuilder extends Builder<StyleResponse> {

  private StyleDTO style;

  private StyleResponseBuilder() {

  }

  public StyleResponseBuilder style(StyleDTO style) {
    this.style = style;
    return this;
  }

  @Override
  public StyleResponse build() {
    StyleResponse response = new StyleResponse();
    response.setStyle(style);
    return response;
  }

  public static StyleResponseBuilder create() {
    return new StyleResponseBuilder();
  }
}
