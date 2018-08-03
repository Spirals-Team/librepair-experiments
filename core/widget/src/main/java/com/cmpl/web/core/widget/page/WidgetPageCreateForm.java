package com.cmpl.web.core.widget.page;

import javax.validation.constraints.NotBlank;

public class WidgetPageCreateForm {

  @NotBlank(message = "empty.widget.page.id")
  private String pageId;
  @NotBlank(message = "empty.widget.id")
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
