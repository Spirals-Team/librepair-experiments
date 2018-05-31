package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetBuilder extends BaseBuilder<Widget> {

  private String type;

  private String entityId;

  private String name;

  private WidgetBuilder() {

  }

  public WidgetBuilder type(String type) {
    this.type = type;
    return this;
  }

  public WidgetBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public WidgetBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Widget build() {
    Widget widget = new Widget();
    widget.setEntityId(entityId);
    widget.setName(name);
    widget.setType(type);
    return widget;
  }

  public static WidgetBuilder create() {
    return new WidgetBuilder();
  }
}
