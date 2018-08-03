package com.cmpl.web.core.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Role;

public class RoleServiceImpl extends BaseServiceImpl<RoleDTO, Role> implements RoleService {

  public RoleServiceImpl(RoleDAO roleDAO, RoleMapper roleMapper) {
    super(roleDAO, roleMapper);
  }

  @Override
  public Page<RoleDTO> searchEntities(PageRequest pageRequest, String query) {
    return null;
  }
}
