package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.WidgetPage;

public class WidgetPageBuilder extends BaseBuilder<WidgetPage> {

  private String pageId;

  private String widgetId;

  private WidgetPageBuilder() {

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
    widgetPage.setCreationDate(creationDate);
    widgetPage.setCreationUser(creationUser);
    widgetPage.setModificationUser(modificationUser);
    widgetPage.setId(id);
    widgetPage.setModificationDate(modificationDate);
    return widgetPage;

  }

  public static WidgetPageBuilder create() {
    return new WidgetPageBuilder();
  }
}
