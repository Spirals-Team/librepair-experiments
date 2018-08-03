package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.resource.BaseResponse;

public class WidgetPageResponse extends BaseResponse {

  private WidgetPageDTO widgetPage;

  public WidgetPageDTO getWidgetPage() {
    return widgetPage;
  }

  public void setWidgetPage(WidgetPageDTO widgetPage) {
    this.widgetPage = widgetPage;
  }
}
