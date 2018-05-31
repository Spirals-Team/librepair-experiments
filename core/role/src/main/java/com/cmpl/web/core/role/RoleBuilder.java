package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.BaseBuilder;

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
    return role;
  }

  public static RoleBuilder create() {
    return new RoleBuilder();
  }
}
