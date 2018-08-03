package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.BOGroup;

public class GroupBuilder extends BaseBuilder<BOGroup> {

  private String name;

  private String description;

  private GroupBuilder() {

  }

  public GroupBuilder description(String description) {
    this.description = description;
    return this;
  }

  public GroupBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public BOGroup build() {
    BOGroup boGroup = new BOGroup();
    boGroup.setDescription(description);
    boGroup.setName(name);
    boGroup.setId(id);
    boGroup.setCreationDate(creationDate);
    boGroup.setModificationDate(modificationDate);
    boGroup.setCreationUser(creationUser);
    boGroup.setModificationUser(modificationUser);
    return boGroup;
  }

  public static GroupBuilder create() {
    return new GroupBuilder();
  }
}
