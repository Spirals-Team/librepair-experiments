package com.cmpl.web.core.group;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.BOGroup;

public class GroupMapper extends BaseMapper<GroupDTO, BOGroup> {

  @Override
  public GroupDTO toDTO(BOGroup entity) {
    GroupDTO dto = GroupDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public BOGroup toEntity(GroupDTO dto) {
    BOGroup entity = GroupBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
