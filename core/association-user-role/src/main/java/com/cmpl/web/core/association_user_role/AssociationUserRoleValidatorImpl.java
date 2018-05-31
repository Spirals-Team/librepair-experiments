package com.cmpl.web.core.association_user_role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class AssociationUserRoleValidatorImpl extends BaseValidator implements AssociationUserRoleValidator {

  public AssociationUserRoleValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(AssociationUserRoleCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getUserId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_USER_ID, locale));
    }
    if (!isStringValid(form.getRoleId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_ROLE_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String userId, String roleId, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(userId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_USER_ID, locale));
    }
    if (!isStringValid(userId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_ROLE_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
