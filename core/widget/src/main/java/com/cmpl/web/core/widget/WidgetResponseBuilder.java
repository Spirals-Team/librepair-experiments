package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class WidgetResponseBuilder extends Builder<WidgetResponse> {

  private WidgetDTO widget;
  private Error error;

  public WidgetResponseBuilder widget(WidgetDTO widget) {
    this.widget = widget;
    return this;
  }

  public WidgetResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  private WidgetResponseBuilder() {

  }

  @Override
  public WidgetResponse build() {
    WidgetResponse response = new WidgetResponse();
    response.setWidget(widget);
    response.setError(error);
    return response;
  }

  public static WidgetResponseBuilder create(){
    return new WidgetResponseBuilder();
  }

}
