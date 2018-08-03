package com.cmpl.web.core.membership;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Membership;

public interface MembershipDAO extends BaseDAO<Membership> {

  List<Membership> findByEntityId(Long entityId);

  Membership findByEntityIdAndGroupId(Long entityId, Long groupId);

}
