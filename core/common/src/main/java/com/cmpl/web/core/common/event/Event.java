package com.cmpl.web.core.common.event;

import org.springframework.context.ApplicationEvent;

import com.cmpl.web.core.models.BaseEntity;

public class Event<ENTITY extends BaseEntity> extends ApplicationEvent {

  protected ENTITY entity;

  public Event(Object source, ENTITY entity) {
    super(source);
    this.entity = entity;
  }

  public ENTITY getEntity() {
    return entity;
  }
}
