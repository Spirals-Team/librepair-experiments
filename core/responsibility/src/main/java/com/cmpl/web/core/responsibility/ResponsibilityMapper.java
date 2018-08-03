package com.cmpl.web.core.responsibility;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Responsibility;

public class ResponsibilityMapper extends BaseMapper<ResponsibilityDTO, Responsibility> {

  @Override
  public ResponsibilityDTO toDTO(Responsibility entity) {
    ResponsibilityDTO dto = ResponsibilityDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Responsibility toEntity(ResponsibilityDTO dto) {
    Responsibility entity = ResponsibilityBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
