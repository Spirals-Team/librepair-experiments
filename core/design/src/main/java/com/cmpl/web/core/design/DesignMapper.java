package com.cmpl.web.core.design;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Design;

public class DesignMapper extends BaseMapper<DesignDTO, Design> {

  @Override
  public DesignDTO toDTO(Design entity) {
    DesignDTO dto = DesignDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Design toEntity(DesignDTO dto) {
    Design entity = DesignBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
