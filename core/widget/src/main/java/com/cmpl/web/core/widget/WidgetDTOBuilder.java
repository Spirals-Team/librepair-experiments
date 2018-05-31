package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetDTOBuilder extends BaseBuilder<WidgetDTO> {

  private String type;

  private String entityId;

  private String name;

  private String personalization;

  private WidgetDTOBuilder() {

  }

  public WidgetDTOBuilder type(String type) {
    this.type = type;
    return this;
  }

  public WidgetDTOBuilder entityId(String entityId) {
    this.entityId = entityId;
    return this;
  }

  public WidgetDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public WidgetDTOBuilder personalization(String personalization) {
    this.personalization = personalization;
    return this;
  }

  @Override
  public WidgetDTO build() {
    WidgetDTO widgetDTO = new WidgetDTO();
    widgetDTO.setEntityId(entityId);
    widgetDTO.setName(name);
    widgetDTO.setType(type);
    widgetDTO.setPersonalization(personalization);
    return widgetDTO;
  }

  public static WidgetDTOBuilder create() {
    return new WidgetDTOBuilder();
  }
}
