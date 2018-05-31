package com.cmpl.web.core.role;

import java.util.ArrayList;
import java.util.List;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class RoleDTOBuilder extends BaseBuilder<RoleDTO> {

  private String name;

  private String description;

  private List<String> privileges;

  private RoleDTOBuilder() {
    privileges = new ArrayList<>();

  }

  public RoleDTOBuilder name(String name) {
    this.name = name;
    return this;
  }

  public RoleDTOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public RoleDTOBuilder privileges(List<String> privileges) {
    this.privileges.addAll(privileges);
    return this;
  }

  @Override
  public RoleDTO build() {
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setDescription(description);
    roleDTO.setName(name);
    roleDTO.setPrivileges(privileges);
    return roleDTO;
  }

  public static RoleDTOBuilder create() {
    return new RoleDTOBuilder();
  }
}
