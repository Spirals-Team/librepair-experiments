package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class WidgetDTOBuilder extends BaseBuilder<WidgetDTO> {

  private String type;

  private String entityId;

  private String name;

  private String personalization;

  private boolean asynchronous;

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

  public WidgetDTOBuilder asynchronous(boolean asynchronous) {
    this.asynchronous = asynchronous;
    return this;
  }

  @Override
  public WidgetDTO build() {
    WidgetDTO widgetDTO = new WidgetDTO();
    widgetDTO.setEntityId(entityId);
    widgetDTO.setName(name);
    widgetDTO.setType(type);
    widgetDTO.setAsynchronous(asynchronous);
    widgetDTO.setPersonalization(personalization);
    widgetDTO.setCreationDate(creationDate);
    widgetDTO.setCreationUser(creationUser);
    widgetDTO.setModificationUser(modificationUser);
    widgetDTO.setId(id);
    widgetDTO.setModificationDate(modificationDate);
    return widgetDTO;
  }

  public static WidgetDTOBuilder create() {
    return new WidgetDTOBuilder();
  }
}
