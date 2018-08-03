package com.cmpl.web.core.membership;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Membership;

public class MembershipServiceImpl extends BaseServiceImpl<MembershipDTO, Membership> implements MembershipService {

  private final MembershipDAO dao;

  public MembershipServiceImpl(MembershipDAO dao, MembershipMapper mapper) {
    super(dao, mapper);
    this.dao = dao;
  }

  @Override
  public List<MembershipDTO> findByEntityId(Long entityId) {
    return mapper.toListDTO(dao.findByEntityId(entityId));
  }

  @Override
  public MembershipDTO findByEntityIdAndGroupId(Long entityId, Long groupId) {
    return mapper.toDTO(dao.findByEntityIdAndGroupId(entityId, groupId));
  }
}
