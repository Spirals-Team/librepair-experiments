package com.cmpl.web.core.group;

import com.cmpl.web.core.common.builder.Builder;

public class GroupCreateFormBuilder extends Builder<GroupCreateForm> {

  private String name;
  private String description;

  private GroupCreateFormBuilder() {

  }

  public GroupCreateFormBuilder name(String name) {
    this.name = name;
    return this;
  }

  public GroupCreateFormBuilder description(String description) {
    this.description = description;
    return this;
  }

  @Override
  public GroupCreateForm build() {
    GroupCreateForm form = new GroupCreateForm();
    form.setDescription(description);
    form.setName(name);
    return form;
  }

  public static GroupCreateFormBuilder create() {
    return new GroupCreateFormBuilder();
  }
}
