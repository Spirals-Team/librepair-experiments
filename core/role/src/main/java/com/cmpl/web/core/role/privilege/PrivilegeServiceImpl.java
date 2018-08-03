package com.cmpl.web.core.role.privilege;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.Privilege;

public class PrivilegeServiceImpl extends BaseServiceImpl<PrivilegeDTO, Privilege> implements PrivilegeService {

  private final PrivilegeDAO privilegeDAO;

  public PrivilegeServiceImpl(PrivilegeDAO privilegeDAO, PrivilegeMapper privilegeMapper) {
    super(privilegeDAO, privilegeMapper);
    this.privilegeDAO = privilegeDAO;
  }

  @Override
  public List<PrivilegeDTO> findByRoleId(String roleId) {
    return mapper.toListDTO(privilegeDAO.findByRoleId(roleId));
  }
}
