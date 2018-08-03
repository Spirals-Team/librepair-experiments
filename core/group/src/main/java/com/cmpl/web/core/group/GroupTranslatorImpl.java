package com.cmpl.web.core.group;

public class GroupTranslatorImpl implements GroupTranslator {

  @Override
  public GroupDTO fromCreateFormToDTO(GroupCreateForm form) {
    return GroupDTOBuilder.create().description(form.getDescription()).name(form.getName()).build();
  }

  @Override
  public GroupDTO fromUpdateFormToDTO(GroupUpdateForm form) {
    return GroupDTOBuilder.create().name(form.getName()).description(form.getDescription()).build();
  }

  @Override
  public GroupResponse fromDTOToResponse(GroupDTO dto) {
    return GroupResponseBuilder.create().group(dto).build();
  }
}
