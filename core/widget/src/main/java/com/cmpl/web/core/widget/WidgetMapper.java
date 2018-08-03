package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Widget;

public class WidgetMapper extends BaseMapper<WidgetDTO, Widget> {

  @Override
  public WidgetDTO toDTO(Widget entity) {
    if (entity == null) {
      return null;
    }
    WidgetDTO dto = WidgetDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Widget toEntity(WidgetDTO dto) {
    Widget entity = WidgetBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
