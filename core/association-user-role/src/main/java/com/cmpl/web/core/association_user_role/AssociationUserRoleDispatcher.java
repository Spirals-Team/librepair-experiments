package com.cmpl.web.core.association_user_role;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface AssociationUserRoleDispatcher {

  AssociationUserRoleResponse createEntity(AssociationUserRoleCreateForm createForm, Locale locale)
      throws BaseException;

  void deleteEntity(String userId, String roleId, Locale locale) throws BaseException;

}
