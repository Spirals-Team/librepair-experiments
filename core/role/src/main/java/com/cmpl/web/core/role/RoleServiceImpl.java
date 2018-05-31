package com.cmpl.web.core.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class RoleServiceImpl extends BaseServiceImpl<RoleDTO, Role> implements RoleService {

  private final PrivilegeService privilegeService;

  public RoleServiceImpl(ApplicationEventPublisher publisher, RoleRepository entityRepository,
      PrivilegeService privilegeService) {
    super(entityRepository, publisher);
    this.privilegeService = privilegeService;
  }

  @Override
  protected RoleDTO toDTO(Role entity) {
    RoleDTO dto = RoleDTOBuilder.create().build();
    fillObject(entity, dto);
    List<PrivilegeDTO> privileges = privilegeService.findByRoleId(String.valueOf(dto.getId()));
    if (!CollectionUtils.isEmpty(privileges)) {
      dto.setPrivileges(privileges.stream().map(privilege -> privilege.getContent()).collect(Collectors.toList()));
    }
    return dto;
  }

  @Override
  protected Role toEntity(RoleDTO dto) {
    Role entity = RoleBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
