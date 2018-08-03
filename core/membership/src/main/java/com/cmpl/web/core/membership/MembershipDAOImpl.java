package com.cmpl.web.core.membership;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Membership;

public class MembershipDAOImpl extends BaseDAOImpl<Membership> implements MembershipDAO {

  private final MembershipRepository entityRepository;

  public MembershipDAOImpl(MembershipRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Membership.class, entityRepository, publisher);
    this.entityRepository = entityRepository;
  }

  @Override
  public List<Membership> findByEntityId(Long entityId) {
    return entityRepository.findByEntityId(entityId);
  }

  @Override
  public Membership findByEntityIdAndGroupId(Long entityId, Long groupId) {
    return entityRepository.findByEntityIdAndGroupId(entityId, groupId);
  }
}
