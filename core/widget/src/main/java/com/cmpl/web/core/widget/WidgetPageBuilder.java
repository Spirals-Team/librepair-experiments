package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetPageBuilder extends BaseBuilder<WidgetPage> {

  private String pageId;

  private String widgetId;


  private WidgetPageBuilder(){

  }

  public WidgetPageBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public WidgetPageBuilder widgetId(String widgetId) {
    this.widgetId = widgetId;
    return this;
  }

  @Override
  public WidgetPage build() {
    WidgetPage widgetPage = new WidgetPage();
    widgetPage.setPageId(pageId);
    widgetPage.setWidgetId(widgetId);
    return widgetPage;

  }

  public static WidgetPageBuilder create(){
    return new WidgetPageBuilder();
  }
}
