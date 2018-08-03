package com.cmpl.web.core.user;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.web.core.common.user.ActionToken;
import com.cmpl.web.core.common.user.ActionTokenService;

public class UserDispatcherImpl implements UserDispatcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDispatcherImpl.class);

  private final UserTranslator translator;
  private final UserService service;
  private final PasswordEncoder passwordEncoder;
  private final ActionTokenService tokenService;

  public UserDispatcherImpl(UserTranslator translator, UserService userService, PasswordEncoder passwordEncoder,
      ActionTokenService tokenService) {

    this.translator = Objects.requireNonNull(translator);
    this.service = Objects.requireNonNull(userService);
    this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    this.tokenService = Objects.requireNonNull(tokenService);
  }

  @Override
  public UserResponse createEntity(UserCreateForm form, Locale locale) {

    UserDTO userToCreate = translator.fromCreateFormToDTO(form);
    userToCreate.setLastPasswordModification(LocalDateTime.now());
    String password = passwordEncoder.encode(UUID.randomUUID().toString());
    userToCreate.setPassword(password);
    UserDTO createdUser = service.createUser(userToCreate, locale);
    return translator.fromDTOToResponse(createdUser);

  }

  @Override
  public UserResponse updateEntity(UserUpdateForm form, Locale locale) {

    UserDTO userToUpdate = service.getEntity(form.getId());
    userToUpdate.setDescription(form.getDescription());
    userToUpdate.setEmail(form.getEmail());
    userToUpdate.setLastConnection(form.getLastConnection());
    userToUpdate.setLogin(form.getLogin());

    UserDTO userUpdated = service.updateEntity(userToUpdate);
    return translator.fromDTOToResponse(userUpdated);
  }

  @Override
  public UserResponse deleteEntity(String userId, Locale locale) {
    service.deleteEntity(Long.parseLong(userId));
    return UserResponseBuilder.create().build();
  }

  @Override
  public RequestPasswordLinkResponse sendChangePasswordLink(String email, Locale locale) {
    UserDTO userDTO = service.findByEmail(email);
    if (userDTO == null) {
      return RequestPasswordLinkResponseBuilder.create().build();
    }

    try {
      service.askPasswordChange(userDTO.getId(), locale);
    } catch (Exception e) {
      LOGGER.error("Unable to send email for password change");
    }
    return RequestPasswordLinkResponseBuilder.create().build();

  }

  @Override
  public ChangePasswordResponse changePassword(ChangePasswordForm form, Locale locale) {

    String token = form.getToken();
    ActionToken actionToken = tokenService.decryptToken(token);
    UserDTO userToUpdate = service.getEntity(actionToken.getUserId());
    String encodedNewPassword = passwordEncoder.encode(form.getPassword());
    userToUpdate.setPassword(encodedNewPassword);
    userToUpdate.setLastPasswordModification(LocalDateTime.now());
    service.updateEntity(userToUpdate);

    return ChangePasswordResponseBuilder.create().build();
  }

}
