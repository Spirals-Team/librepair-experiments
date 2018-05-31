package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetPageDTOBuilder extends BaseBuilder<WidgetPageDTO>{


  private String pageId;

  private String widgetId;

  public WidgetPageDTOBuilder pageId(String pageId) {
    this.pageId = pageId;
    return this;
  }

  public WidgetPageDTOBuilder widgetId(String widgetId) {
    this.widgetId = widgetId;
    return this;
  }

  private WidgetPageDTOBuilder(){

  }

  @Override
  public WidgetPageDTO build() {
    WidgetPageDTO widgetPageDTO = new WidgetPageDTO();
    widgetPageDTO.setPageId(pageId);
    widgetPageDTO.setWidgetId(widgetId);
    return widgetPageDTO;
  }

  public static WidgetPageDTOBuilder create(){
    return new WidgetPageDTOBuilder();
  }
}
