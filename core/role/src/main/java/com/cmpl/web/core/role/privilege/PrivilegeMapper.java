package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Privilege;

public class PrivilegeMapper extends BaseMapper<PrivilegeDTO, Privilege> {

  @Override
  public PrivilegeDTO toDTO(Privilege entity) {
    PrivilegeDTO dto = PrivilegeDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Privilege toEntity(PrivilegeDTO dto) {
    Privilege entity = PrivilegeBuilder.create().build();
    fillObject(dto, entity);

    return entity;
  }
}
