package com.cmpl.web.core.user.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cmpl.web.core.common.user.ActionToken;
import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.user.UserService;

public class TokenValidator implements ConstraintValidator<ValidToken, String> {

  private final ActionTokenService tokenService;

  public TokenValidator(ActionTokenService tokenService) {
    this.tokenService = Objects.requireNonNull(tokenService);

  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    ActionToken actionToken = tokenService.decryptToken(value);

    return actionToken.isValid() && (actionToken.getAction().equals(UserService.USER_RESET_PASSWORD))
        || actionToken.getAction().equals(UserService.USER_ACTIVATION);
  }
}
