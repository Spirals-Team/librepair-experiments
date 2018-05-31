package com.cmpl.web.core.style;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class StyleResponseBuilder extends Builder<StyleResponse> {

  private StyleDTO style;
  private Error error;

  private StyleResponseBuilder() {

  }

  public StyleResponseBuilder style(StyleDTO style) {
    this.style = style;
    return this;
  }

  public StyleResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  @Override
  public StyleResponse build() {
    StyleResponse response = new StyleResponse();
    response.setStyle(style);
    response.setError(error);
    return response;
  }

  public static StyleResponseBuilder create() {
    return new StyleResponseBuilder();
  }
}
