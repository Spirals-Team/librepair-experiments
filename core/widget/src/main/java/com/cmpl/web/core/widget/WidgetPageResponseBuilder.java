package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;
import com.cmpl.web.core.common.error.Error;

public class WidgetPageResponseBuilder extends Builder<WidgetPageResponse> {

  private WidgetPageDTO widgetPage;
  private Error error;

  public WidgetPageResponseBuilder widgetPage(WidgetPageDTO widgetPage) {
    this.widgetPage = widgetPage;
    return this;
  }

  public WidgetPageResponseBuilder error(Error error) {
    this.error = error;
    return this;
  }

  private WidgetPageResponseBuilder() {

  }

  @Override
  public WidgetPageResponse build() {
    WidgetPageResponse widgetPageResponse = new WidgetPageResponse();
    widgetPageResponse.setWidgetPage(widgetPage);
    widgetPageResponse.setError(error);
    return widgetPageResponse;
  }

  public static WidgetPageResponseBuilder create() {
    return new WidgetPageResponseBuilder();
  }
}
