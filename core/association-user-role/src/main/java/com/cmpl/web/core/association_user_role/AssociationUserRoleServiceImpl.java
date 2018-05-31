package com.cmpl.web.core.association_user_role;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.service.BaseServiceImpl;

public class AssociationUserRoleServiceImpl extends BaseServiceImpl<AssociationUserRoleDTO, AssociationUserRole>
    implements AssociationUserRoleService {

  private final AssociationUserRoleRepository entityRepository;

  public AssociationUserRoleServiceImpl(ApplicationEventPublisher publisher,
      AssociationUserRoleRepository entityRepository) {
    super(entityRepository, publisher);
    this.entityRepository = entityRepository;
  }

  @Override
  public List<AssociationUserRoleDTO> findByUserId(String userId) {
    return toListDTO(entityRepository.findByUserId(userId));
  }

  @Override
  public List<AssociationUserRoleDTO> findByRoleId(String roleId) {
    return toListDTO(entityRepository.findByRoleId(roleId));
  }

  @Override
  public AssociationUserRoleDTO findByUserIdAndRoleId(String userId, String roleId) {
    return toDTO(entityRepository.findByUserIdAndRoleId(userId, roleId));
  }

  @Override
  protected AssociationUserRoleDTO toDTO(AssociationUserRole entity) {
    AssociationUserRoleDTO dto = AssociationUserRoleDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected AssociationUserRole toEntity(AssociationUserRoleDTO dto) {
    AssociationUserRole entity = AssociationUserRoleBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
