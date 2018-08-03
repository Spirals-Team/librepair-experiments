package com.cmpl.web.core.widget.page;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.WidgetPage;

public class WidgetPageMapper extends BaseMapper<WidgetPageDTO, WidgetPage> {

  @Override
  public WidgetPageDTO toDTO(WidgetPage entity) {
    if (entity == null) {
      return null;
    }
    WidgetPageDTO dto = WidgetPageDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public WidgetPage toEntity(WidgetPageDTO dto) {
    WidgetPage entity = WidgetPageBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
