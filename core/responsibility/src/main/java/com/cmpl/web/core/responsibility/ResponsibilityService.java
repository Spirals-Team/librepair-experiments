package com.cmpl.web.core.responsibility;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface ResponsibilityService extends BaseService<ResponsibilityDTO> {

  List<ResponsibilityDTO> findByUserId(String userId);

  List<ResponsibilityDTO> findByRoleId(String roleId);

  ResponsibilityDTO findByUserIdAndRoleId(String userId, String roleId);

}
