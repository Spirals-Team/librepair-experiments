package com.cmpl.web.core.membership;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;
import com.cmpl.web.core.models.Membership;

@Repository
public interface MembershipRepository extends BaseRepository<Membership> {

  List<Membership> findByEntityId(Long entityId);

  Membership findByEntityIdAndGroupId(Long entityId, Long groupId);

}
