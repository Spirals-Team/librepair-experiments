package com.cmpl.web.core.membership;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class MembershipDTOBuilder extends BaseBuilder<MembershipDTO> {

  private Long groupId;
  private Long entityId;

  public MembershipDTOBuilder groupId(Long groupId) {
    this.groupId = groupId;
    return this;
  }

  public MembershipDTOBuilder entityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  private MembershipDTOBuilder() {

  }

  @Override
  public MembershipDTO build() {
    MembershipDTO membershipDTO = new MembershipDTO();
    membershipDTO.setGroupId(groupId);
    membershipDTO.setEntityId(entityId);
    membershipDTO.setCreationDate(creationDate);
    membershipDTO.setCreationUser(creationUser);
    membershipDTO.setModificationUser(modificationUser);
    membershipDTO.setId(id);
    membershipDTO.setModificationDate(modificationDate);
    return membershipDTO;
  }

  public static MembershipDTOBuilder create() {
    return new MembershipDTOBuilder();
  }
}
