package com.cmpl.web.core.common.event;

import org.springframework.context.ApplicationEvent;

import com.cmpl.web.core.common.dto.BaseDTO;

public class Event<D extends BaseDTO> extends ApplicationEvent {

  protected D dto;

  public Event(Object source, D dto) {
    super(source);
    this.dto = dto;
  }

  public D getDto() {
    return dto;
  }
}
