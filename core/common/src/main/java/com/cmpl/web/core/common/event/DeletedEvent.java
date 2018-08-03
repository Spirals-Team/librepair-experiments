package com.cmpl.web.core.common.event;

import com.cmpl.web.core.models.BaseEntity;

public class DeletedEvent<ENTITY extends BaseEntity> extends Event<ENTITY> {

  public DeletedEvent(Object source, ENTITY entity) {
    super(source, entity);
  }
}
