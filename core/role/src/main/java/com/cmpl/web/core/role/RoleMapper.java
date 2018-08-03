package com.cmpl.web.core.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.mapper.BaseMapper;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.role.privilege.PrivilegeDTO;
import com.cmpl.web.core.role.privilege.PrivilegeService;

public class RoleMapper extends BaseMapper<RoleDTO, Role> {

  private final PrivilegeService privilegeService;

  public RoleMapper(PrivilegeService privilegeService) {
    this.privilegeService = privilegeService;
  }

  @Override
  public RoleDTO toDTO(Role entity) {
    RoleDTO dto = RoleDTOBuilder.create().build();
    fillObject(entity, dto);
    List<PrivilegeDTO> privileges = privilegeService.findByRoleId(String.valueOf(dto.getId()));
    if (!CollectionUtils.isEmpty(privileges)) {
      dto.setPrivileges(privileges.stream().map(privilege -> privilege.getContent()).collect(Collectors.toList()));
    }
    return dto;
  }

  @Override
  public Role toEntity(RoleDTO dto) {
    Role entity = RoleBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
