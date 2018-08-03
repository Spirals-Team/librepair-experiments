package com.cmpl.web.core.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cmpl.web.core.user.ChangePasswordForm;

public class PasswordConfirmationValidator implements ConstraintValidator<PasswordConfirmation, ChangePasswordForm> {

  @Override
  public boolean isValid(ChangePasswordForm value, ConstraintValidatorContext context) {

    if (!value.getPassword().equals(value.getConfirmation())) {
      return false;
    }

    return true;
  }
}
