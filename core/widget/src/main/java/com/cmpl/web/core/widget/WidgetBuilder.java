package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Widget;

public class WidgetBuilder extends BaseBuilder<Widget> {

  private String type;

  private String entityId;

  private String name;

  private boolean asynchronous;

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

  public WidgetBuilder asynchronous(boolean asynchronous) {
    this.asynchronous = asynchronous;
    return this;
  }

  @Override
  public Widget build() {
    Widget widget = new Widget();
    widget.setEntityId(entityId);
    widget.setName(name);
    widget.setType(type);
    widget.setCreationDate(creationDate);
    widget.setCreationUser(creationUser);
    widget.setModificationUser(modificationUser);
    widget.setId(id);
    widget.setModificationDate(modificationDate);
    widget.setAsynchronous(asynchronous);
    return widget;
  }

  public static WidgetBuilder create() {
    return new WidgetBuilder();
  }
}
