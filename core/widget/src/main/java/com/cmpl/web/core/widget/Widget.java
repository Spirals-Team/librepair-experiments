package com.cmpl.web.core.widget;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cmpl.web.core.common.dao.BaseEntity;

@Entity(name = "widget")
@Table(name = "widget")
public class Widget extends BaseEntity {

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "entity_id")
  private String entityId;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

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
}
