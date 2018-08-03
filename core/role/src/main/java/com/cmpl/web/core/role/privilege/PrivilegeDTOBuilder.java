package com.cmpl.web.core.role.privilege;

import com.cmpl.web.core.common.builder.BaseBuilder;

public class PrivilegeDTOBuilder extends BaseBuilder<PrivilegeDTO> {

  private String roleId;

  private String content;

  private PrivilegeDTOBuilder() {

  }

  public PrivilegeDTOBuilder roleId(String roleId) {
    this.roleId = roleId;
    return this;
  }

  public PrivilegeDTOBuilder content(String content) {
    this.content = content;
    return this;
  }

  @Override
  public PrivilegeDTO build() {
    PrivilegeDTO privilegeDTO = new PrivilegeDTO();
    privilegeDTO.setContent(content);
    privilegeDTO.setRoleId(roleId);
    privilegeDTO.setCreationDate(creationDate);
    privilegeDTO.setCreationUser(creationUser);
    privilegeDTO.setModificationUser(modificationUser);
    privilegeDTO.setId(id);
    privilegeDTO.setModificationDate(modificationDate);
    return privilegeDTO;
  }

  public static PrivilegeDTOBuilder create() {
    return new PrivilegeDTOBuilder();
  }
}
