package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.Builder;

public class WidgetPageCreateFormBuilder extends Builder<WidgetPageCreateForm> {

  private String pageId;
  private String widgetId;

  public WidgetPageCreateFormBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public WidgetPageCreateFormBuilder widgetId(String widgetId) {
    this.widgetId = widgetId;
    return this;
  }

  private WidgetPageCreateFormBuilder() {

  }

  @Override
  public WidgetPageCreateForm build() {
    WidgetPageCreateForm form = new WidgetPageCreateForm();
    form.setPageId(pageId);
    form.setWidgetId(widgetId);
    return form;
  }

  public static WidgetPageCreateFormBuilder create() {
    return new WidgetPageCreateFormBuilder();
  }
}
