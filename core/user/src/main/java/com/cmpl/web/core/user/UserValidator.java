package com.cmpl.web.core.user;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.user.ActionToken;

public interface UserValidator {

  Error validateCreate(UserCreateForm form, Locale locale);

  Error validateUpdate(UserUpdateForm form, Locale locale);

  Error validateChangePassword(ChangePasswordForm form, Locale locale);

  Error validateNewPassword(String oldPassword, String newPassword, String encodedNewPassword, Locale locale);

  Error validateToken(ActionToken actionToken, Locale locale);

}
