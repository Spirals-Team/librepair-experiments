package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetResponseBuilder extends Builder<WidgetResponse> {

  private WidgetDTO widget;

  public WidgetResponseBuilder widget(WidgetDTO widget) {
    this.widget = widget;
    return this;
  }

  private WidgetResponseBuilder() {

  }

  @Override
  public WidgetResponse build() {
    WidgetResponse response = new WidgetResponse();
    response.setWidget(widget);
    return response;
  }

  public static WidgetResponseBuilder create() {
    return new WidgetResponseBuilder();
  }

}
