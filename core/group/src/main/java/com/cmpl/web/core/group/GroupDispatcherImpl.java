package com.cmpl.web.core.group;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.resource.BaseResponse;

public class GroupDispatcherImpl implements GroupDispatcher {

  private final GroupTranslator translator;
  private final GroupService service;

  public GroupDispatcherImpl(GroupTranslator translator, GroupService service) {

    this.service = Objects.requireNonNull(service);
    this.translator = Objects.requireNonNull(translator);
  }

  @Override
  public GroupResponse createEntity(GroupCreateForm form, Locale locale) {

    GroupDTO groupToCreate = translator.fromCreateFormToDTO(form);
    GroupDTO createdGroup = service.createEntity(groupToCreate);

    return translator.fromDTOToResponse(createdGroup);
  }

  @Override
  public GroupResponse updateEntity(GroupUpdateForm form, Locale locale) {

    GroupDTO groupToUpdate = service.getEntity(form.getId());
    groupToUpdate.setDescription(form.getDescription());
    groupToUpdate.setName(form.getName());

    GroupDTO updatedGroup = service.updateEntity(groupToUpdate);

    return translator.fromDTOToResponse(updatedGroup);
  }

  @Override
  public BaseResponse deleteEntity(String groupId, Locale locale) {
    service.deleteEntity(Long.parseLong(groupId));
    return GroupResponseBuilder.create().build();
  }
}
