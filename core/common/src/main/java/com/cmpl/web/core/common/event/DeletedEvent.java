package com.cmpl.web.core.common.event;

import com.cmpl.web.core.common.dto.BaseDTO;

public class DeletedEvent<D extends BaseDTO> extends Event<D> {

  public DeletedEvent(Object source, D dto) {
    super(source, dto);
  }
}
