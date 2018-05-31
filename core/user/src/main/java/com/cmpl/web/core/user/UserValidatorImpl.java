package com.cmpl.web.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.ActionToken;
import com.cmpl.web.core.common.validator.BaseValidator;

public class UserValidatorImpl extends BaseValidator implements UserValidator {

  private static final String REGEX_AT_LEAST_ONE_NUMERIC_CHARACTER = ".*[0-9].*";
  private static final String REGEX_AT_LEAST_ONE_LOWER_CASE_CHARACTER = ".*[a-z].*";
  private static final String REGEX_AT_LEAST_ONE_UPPER_CASE_CHARACTER = ".*[A-Z].*";
  private static final String REGEX_AT_LEAST_ONE_SPECIAL_CHARACTER = ".*[^a-zA-Z0-9].*";
  private static final int USER_PASSWORD_MINIMUM_SIZE = 8;

  public UserValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(UserCreateForm form, Locale locale) {
    return validate(form.getLogin(), form.getEmail(), locale);
  }

  @Override
  public Error validateUpdate(UserUpdateForm form, Locale locale) {
    return validate(form.getLogin(), form.getEmail(), locale);
  }

  @Override
  public Error validateChangePassword(ChangePasswordForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getPassword())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PASSWORD, locale));
    }
    if (!isStringValid(form.getConfirmation())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CONFIRMATION, locale));
    }
    if (!form.getConfirmation().equals(form.getPassword())) {
      causes.add(computeCause(ERROR_CAUSE.PASSWORD_CONFIRMATION_DIFFERENT, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateNewPassword(String oldPassword, String newPassword, String encodedNewPassword, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (oldPassword.equals(encodedNewPassword)) {
      causes.add(computeCause(ERROR_CAUSE.PASSWORD_SAME_AS_OLD, locale));
    }
    if (!isPasswordValid(newPassword)) {
      causes.add(computeCause(ERROR_CAUSE.PASSWORD_NOT_STRONG, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateToken(ActionToken actionToken, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();

    if (!actionToken.isValid() || (!actionToken.getAction().equals(UserService.USER_RESET_PASSWORD))
        && !actionToken.getAction().equals(UserService.USER_ACTIVATION)) {
      causes.add(computeCause(ERROR_CAUSE.INVALID_TOKEN, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  boolean isPasswordValid(String password) {
    return password.length() >= USER_PASSWORD_MINIMUM_SIZE && password.matches(REGEX_AT_LEAST_ONE_NUMERIC_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_LOWER_CASE_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_UPPER_CASE_CHARACTER)
        && password.matches(REGEX_AT_LEAST_ONE_SPECIAL_CHARACTER);
  }

  Error validate(String login, String email, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(login)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_LOGIN, locale));
    }
    if (!isStringValid(email)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_EMAIL, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
