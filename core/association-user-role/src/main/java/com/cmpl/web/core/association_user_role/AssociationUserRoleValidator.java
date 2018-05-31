package com.cmpl.web.core.association_user_role;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface AssociationUserRoleValidator {

  Error validateCreate(AssociationUserRoleCreateForm form, Locale locale);

  Error validateDelete(String userId, String roleId, Locale locale);

}
