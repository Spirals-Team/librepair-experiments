package com.cmpl.web.core.role;

import java.util.Locale;

import com.cmpl.web.core.common.resource.BaseResponse;
import com.cmpl.web.core.role.privilege.PrivilegeForm;
import com.cmpl.web.core.role.privilege.PrivilegeResponse;

public interface RoleDispatcher {

  RoleResponse createEntity(RoleCreateForm form, Locale locale);

  RoleResponse updateEntity(RoleUpdateForm form, Locale locale);

  BaseResponse deleteEntity(String roleId, Locale locale);

  PrivilegeResponse updateEntity(PrivilegeForm form, Locale locale);

}
