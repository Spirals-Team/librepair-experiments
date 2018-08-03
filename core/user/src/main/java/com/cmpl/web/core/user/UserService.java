package com.cmpl.web.core.user;

import java.time.LocalDateTime;
import java.util.Locale;

import com.cmpl.web.core.common.service.BaseService;

public interface UserService extends BaseService<UserDTO> {

  String USER_ACTIVATION = "USER_ACTIVATION";
  String USER_RESET_PASSWORD = "USER_RESET_PASSWORD";

  UserDTO findByLogin(String login);

  UserDTO findByEmail(String email);

  UserDTO updateLastConnection(Long userId, LocalDateTime connectionDateTime);

  void askPasswordChange(long userId, Locale locale) throws Exception;

  UserDTO createUser(UserDTO dto, Locale locale);

  String generatePasswordResetToken(UserDTO user);
}
