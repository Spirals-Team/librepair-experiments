package com.cmpl.web.core.role;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class PrivilegeServiceImpl extends BaseServiceImpl<PrivilegeDTO, Privilege> implements PrivilegeService {

  private final PrivilegeRepository privilegeRepository;

  public PrivilegeServiceImpl(ApplicationEventPublisher publisher, PrivilegeRepository privilegeRepository) {
    super(privilegeRepository, publisher);
    this.privilegeRepository = privilegeRepository;
  }

  @Override
  protected PrivilegeDTO toDTO(Privilege entity) {
    PrivilegeDTO dto = PrivilegeDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Privilege toEntity(PrivilegeDTO dto) {
    Privilege entity = PrivilegeBuilder.create().build();
    fillObject(dto, entity);

    return entity;
  }

  @Override
  public List<PrivilegeDTO> findByRoleId(String roleId) {
    return toListDTO(privilegeRepository.findByRoleId(roleId));
  }
}
