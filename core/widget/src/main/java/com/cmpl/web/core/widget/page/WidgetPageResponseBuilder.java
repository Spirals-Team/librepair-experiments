package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetPageResponseBuilder extends Builder<WidgetPageResponse> {

  private WidgetPageDTO widgetPage;

  public WidgetPageResponseBuilder widgetPage(WidgetPageDTO widgetPage) {
    this.widgetPage = widgetPage;
    return this;
  }

  private WidgetPageResponseBuilder() {

  }

  @Override
  public WidgetPageResponse build() {
    WidgetPageResponse widgetPageResponse = new WidgetPageResponse();
    widgetPageResponse.setWidgetPage(widgetPage);
    return widgetPageResponse;
  }

  public static WidgetPageResponseBuilder create() {
    return new WidgetPageResponseBuilder();
  }
}
