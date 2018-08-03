package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.resource.BaseResponse;

public class WidgetResponse extends BaseResponse {

  private WidgetDTO widget;

  public WidgetDTO getWidget() {
    return widget;
  }

  public void setWidget(WidgetDTO widget) {
    this.widget = widget;
  }
}
