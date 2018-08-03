package com.cmpl.web.core.role;

import com.cmpl.web.core.common.builder.Builder;

public class RoleCreateFormBuilder extends Builder<RoleCreateForm> {

  private String name;
  private String description;

  private RoleCreateFormBuilder() {

  }

  public RoleCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RoleCreateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  @Override
  public RoleCreateForm build() {
    RoleCreateForm form = new RoleCreateForm();
    form.setDescription(description);
    form.setName(name);
    return form;
  }

  public static RoleCreateFormBuilder create() {
    return new RoleCreateFormBuilder();
  }
}
