package com.cmpl.web.core.membership;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface MembershipService extends BaseService<MembershipDTO> {

  List<MembershipDTO> findByEntityId(Long entityId);

  MembershipDTO findByEntityIdAndGroupId(Long entityId, Long groupId);

}
