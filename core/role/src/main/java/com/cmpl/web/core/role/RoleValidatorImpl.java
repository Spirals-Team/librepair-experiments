package com.cmpl.web.core.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class RoleValidatorImpl extends BaseValidator implements RoleValidator {

  public RoleValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(RoleCreateForm form, Locale locale) {
    return validate(form.getName(), form.getDescription(), locale);
  }

  @Override
  public Error validateUpdate(RoleUpdateForm form, Locale locale) {
    return validate(form.getName(), form.getDescription(), locale);
  }

  @Override
  public Error validateUpdate(PrivilegeForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (CollectionUtils.isEmpty(form.getPrivilegesToEnable())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PRIVILEGES, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  Error validate(String name, String description, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_ROLE_NAME, locale));
    }
    if (!isStringValid(description)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_ROLE_DESCRIPTION, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
