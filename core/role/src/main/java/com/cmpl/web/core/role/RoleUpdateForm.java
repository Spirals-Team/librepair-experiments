package com.cmpl.web.core.role;

import javax.validation.constraints.NotBlank;

import com.cmpl.web.core.common.form.BaseUpdateForm;

public class RoleUpdateForm extends BaseUpdateForm<RoleDTO> {

  @NotBlank(message = "empty.role.name")
  private String name;
  @NotBlank(message = "empty.role.description")
  private String description;

  public RoleUpdateForm() {

  }

  public RoleUpdateForm(RoleDTO roleDTO) {
    super(roleDTO);
    this.name = roleDTO.getName();
    this.description = roleDTO.getDescription();
  }

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
