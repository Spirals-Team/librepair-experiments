package com.cmpl.web.core.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

  private static final String REGEX_AT_LEAST_ONE_NUMERIC_CHARACTER = ".*[0-9].*";
  private static final String REGEX_AT_LEAST_ONE_LOWER_CASE_CHARACTER = ".*[a-z].*";
  private static final String REGEX_AT_LEAST_ONE_UPPER_CASE_CHARACTER = ".*[A-Z].*";
  private static final String REGEX_AT_LEAST_ONE_SPECIAL_CHARACTER = ".*[^a-zA-Z0-9].*";
  private static final int USER_PASSWORD_MINIMUM_SIZE = 8;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return isPasswordValid(value);
  }

  private boolean isPasswordValid(String password) {
    return password.length() >= USER_PASSWORD_MINIMUM_SIZE && password.matches(REGEX_AT_LEAST_ONE_NUMERIC_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_LOWER_CASE_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_UPPER_CASE_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_SPECIAL_CHARACTER);
  }
}
