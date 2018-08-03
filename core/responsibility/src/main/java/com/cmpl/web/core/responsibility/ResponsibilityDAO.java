package com.cmpl.web.core.responsibility;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Responsibility;

public interface ResponsibilityDAO extends BaseDAO<Responsibility> {

  List<Responsibility> findByUserId(String userId);

  List<Responsibility> findByRoleId(String roleId);

  Responsibility findByUserIdAndRoleId(String userId, String roleId);

}
