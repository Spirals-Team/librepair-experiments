package com.cmpl.web.core.membership;

public class MembershipTranslatorImpl implements MembershipTranslator {

  @Override
  public MembershipDTO fromCreateFormToDTO(MembershipCreateForm form) {
    return MembershipDTOBuilder.create().entityId(Long.parseLong(form.getEntityId()))
        .groupId(Long.parseLong(form.getGroupId())).build();
  }

  @Override
  public MembershipResponse fromDTOToResponse(MembershipDTO dto) {
    return MembershipResponseBuilder.create().membership(dto).build();
  }
}
