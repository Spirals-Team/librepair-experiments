package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class GroupDTOBuilder extends BaseBuilder<GroupDTO> {

  private String name;

  private String description;

  private GroupDTOBuilder() {

  }

  public GroupDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public GroupDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public GroupDTO build() {
    GroupDTO group = new GroupDTO();
    group.setDescription(description);
    group.setName(name);
    group.setId(id);
    group.setCreationDate(creationDate);
    group.setModificationDate(modificationDate);
    group.setCreationUser(creationUser);
    group.setModificationUser(modificationUser);
    return group;
  }

  public static GroupDTOBuilder create() {
    return new GroupDTOBuilder();
  }
}
