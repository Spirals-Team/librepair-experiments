package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dto.BaseDTO;

public class WidgetDTO extends BaseDTO {

  private String type;

  private String entityId;

  private String name;

  private String personalization;

  private boolean asynchronous;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPersonalization() {
    return personalization;
  }

  public void setPersonalization(String personalization) {
    this.personalization = personalization;
  }

  public boolean isAsynchronous() {
    return asynchronous;
  }

  public void setAsynchronous(boolean asynchronous) {
    this.asynchronous = asynchronous;
  }
}
