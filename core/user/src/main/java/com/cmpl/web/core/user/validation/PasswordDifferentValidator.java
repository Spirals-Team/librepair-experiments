package com.cmpl.web.core.user.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.web.core.common.user.ActionToken;
import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.user.ChangePasswordForm;
import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;

public class PasswordDifferentValidator implements ConstraintValidator<PasswordDifferent, ChangePasswordForm> {

  private final ActionTokenService tokenService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public PasswordDifferentValidator(UserService userService, ActionTokenService tokenService,
      PasswordEncoder passwordEncoder) {
    this.tokenService = Objects.requireNonNull(tokenService);
    this.userService = Objects.requireNonNull(userService);
    this.passwordEncoder = Objects.requireNonNull(passwordEncoder);

  }

  @Override
  public boolean isValid(ChangePasswordForm value, ConstraintValidatorContext context) {

    ActionToken actionToken = tokenService.decryptToken(value.getToken());
    UserDTO user = userService.getEntity(actionToken.getUserId());
    String encodedNewPassword = passwordEncoder.encode(value.getPassword());

    return !encodedNewPassword.equals(user.getPassword());
  }
}
