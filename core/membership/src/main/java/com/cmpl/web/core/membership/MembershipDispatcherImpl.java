package com.cmpl.web.core.membership;

import java.util.Locale;
import java.util.Objects;

public class MembershipDispatcherImpl implements MembershipDispatcher {

  private final MembershipService service;

  private final MembershipTranslator translator;

  public MembershipDispatcherImpl(MembershipService service, MembershipTranslator translator) {
    this.service = Objects.requireNonNull(service);
    this.translator = Objects.requireNonNull(translator);
  }

  @Override
  public MembershipResponse createEntity(MembershipCreateForm createForm, Locale locale) {
    MembershipDTO membershipToCreate = MembershipDTOBuilder.create().groupId(Long.parseLong(createForm.getGroupId()))
        .entityId(Long.parseLong(createForm.getEntityId())).build();
    return translator.fromDTOToResponse(service.createEntity(membershipToCreate));
  }

  @Override
  public void deleteEntity(String entityId, String groupId, Locale locale) {
    MembershipDTO membershipToDelete = service.findByEntityIdAndGroupId(Long.parseLong(entityId),
        Long.parseLong(groupId));
    service.deleteEntity(membershipToDelete.getId());
  }
}
