package com.cmpl.web.core.group;

import javax.validation.constraints.NotBlank;

public class GroupCreateForm {

  @NotBlank(message = "empty.group.name")
  private String name;

  @NotBlank(message = "empty.group.description")
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
