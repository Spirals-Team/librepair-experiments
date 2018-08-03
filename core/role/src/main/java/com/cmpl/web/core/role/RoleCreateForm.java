package com.cmpl.web.core.role;

import javax.validation.constraints.NotBlank;

public class RoleCreateForm {

  @NotBlank(message = "empty.role.name")
  private String name;
  @NotBlank(message = "empty.role.description")
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
