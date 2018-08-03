package com.cmpl.web.core.style;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Style;

public class StyleDAOImpl extends BaseDAOImpl<Style> implements StyleDAO {

  private final StyleRepository styleRepository;

  public StyleDAOImpl(StyleRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Style.class, entityRepository, publisher);
    this.styleRepository = entityRepository;
  }

  @Override
  public Style getStyle() {
    return styleRepository.findAll().get(0);
  }
}
