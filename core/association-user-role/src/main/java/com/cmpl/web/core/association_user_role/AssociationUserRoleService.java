package com.cmpl.web.core.association_user_role;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface AssociationUserRoleService extends BaseService<AssociationUserRoleDTO> {

  List<AssociationUserRoleDTO> findByUserId(String userId);

  List<AssociationUserRoleDTO> findByRoleId(String roleId);

  AssociationUserRoleDTO findByUserIdAndRoleId(String userId, String roleId);

}
