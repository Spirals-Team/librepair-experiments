package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WidgetPageDTO extends BaseDTO{

  private String pageId;

  private String widgetId;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(String widgetId) {
    this.widgetId = widgetId;
  }
}
