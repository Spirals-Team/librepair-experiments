package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Membership;

public class MembershipBuilder extends BaseBuilder<Membership> {

  private Long groupId;
  private Long entityId;

  public MembershipBuilder groupId(Long groupId) {
    this.groupId = groupId;
    return this;
  }

  public MembershipBuilder entityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  private MembershipBuilder() {

  }

  @Override
  public Membership build() {
    Membership membership = new Membership();
    membership.setGroupId(groupId);
    membership.setEntityId(entityId);
    membership.setCreationDate(creationDate);
    membership.setCreationUser(creationUser);
    membership.setModificationUser(modificationUser);
    membership.setId(id);
    membership.setModificationDate(modificationDate);
    return membership;
  }

  public static MembershipBuilder create() {
    return new MembershipBuilder();
  }
}
