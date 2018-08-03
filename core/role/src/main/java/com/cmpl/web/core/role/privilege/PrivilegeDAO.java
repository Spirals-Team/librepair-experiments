package com.cmpl.web.core.role.privilege;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Privilege;

public interface PrivilegeDAO extends BaseDAO<Privilege> {

  List<Privilege> findByRoleId(String roleId);
}
