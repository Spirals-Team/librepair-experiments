package com.cmpl.web.core.group;

public interface GroupTranslator {

  GroupDTO fromCreateFormToDTO(GroupCreateForm form);

  GroupDTO fromUpdateFormToDTO(GroupUpdateForm form);

  GroupResponse fromDTOToResponse(GroupDTO dto);

}
