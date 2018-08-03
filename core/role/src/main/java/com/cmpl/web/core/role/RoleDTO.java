package com.cmpl.web.core.role;

import java.util.List;

import com.cmpl.web.core.common.dto.BaseDTO;

public class RoleDTO extends BaseDTO {

  private String name;

  private String description;

  private List<String> privileges;

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

  public List<String> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<String> privileges) {
    this.privileges = privileges;
  }
}
