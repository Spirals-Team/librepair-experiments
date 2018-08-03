package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.BaseBuilder;
import com.cmpl.web.core.models.Role;

public class RoleBuilder extends BaseBuilder<Role> {

  private String name;

  private String description;

  private RoleBuilder() {

  }

  public RoleBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RoleBuilder name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Role build() {
    Role role = new Role();
    role.setDescription(description);
    role.setName(name);
    role.setId(id);
    role.setCreationDate(creationDate);
    role.setModificationDate(modificationDate);
    role.setCreationUser(creationUser);
    role.setModificationUser(modificationUser);
    return role;
  }

  public static RoleBuilder create() {
    return new RoleBuilder();
  }
}
