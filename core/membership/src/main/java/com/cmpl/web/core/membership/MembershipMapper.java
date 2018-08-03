package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Membership;

public class MembershipMapper extends BaseMapper<MembershipDTO, Membership> {

  @Override
  public MembershipDTO toDTO(Membership entity) {
    MembershipDTO dto = MembershipDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Membership toEntity(MembershipDTO dto) {
    Membership entity = MembershipBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
