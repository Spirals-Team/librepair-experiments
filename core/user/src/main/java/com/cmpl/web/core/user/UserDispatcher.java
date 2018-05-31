package com.cmpl.web.core.user;

import java.util.Locale;

public interface UserDispatcher {

  UserResponse createEntity(UserCreateForm form, Locale locale);

  UserResponse updateEntity(UserUpdateForm form, Locale locale);

  UserResponse deleteEntity(String userId, Locale locale);

  RequestPasswordLinkResponse sendChangePasswordLink(String email, Locale locale);

  ChangePasswordResponse changePassword(ChangePasswordForm form, Locale locale);

}
