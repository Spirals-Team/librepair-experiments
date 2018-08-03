package com.cmpl.web.core.role.privilege;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Privilege;

public class PrivilegeDAOImpl extends BaseDAOImpl<Privilege> implements PrivilegeDAO {

  private final PrivilegeRepository privilegeRepository;

  public PrivilegeDAOImpl(PrivilegeRepository privilegeRepository, ApplicationEventPublisher publisher) {
    super(Privilege.class, privilegeRepository, publisher);
    this.privilegeRepository = privilegeRepository;
  }

  @Override
  public List<Privilege> findByRoleId(String roleId) {
    return privilegeRepository.findByRoleId(roleId);
  }
}
