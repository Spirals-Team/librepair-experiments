package com.cmpl.web.core.user;

import java.util.Locale;

public interface UserMailService {

  void sendAccountCreationEmail(UserDTO user, String token, Locale locale) throws Exception;

  void sendChangePasswordEmail(UserDTO user, String token, Locale locale) throws Exception;
}
