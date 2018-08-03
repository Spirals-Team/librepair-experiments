package com.cmpl.web.core.role;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.Role;

public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {

  public RoleDAOImpl(RoleRepository entityRepository, ApplicationEventPublisher publisher) {
    super(Role.class, entityRepository, publisher);
  }
}
