package com.cmpl.web.core.responsibility;

import java.util.Locale;

import com.cmpl.web.core.common.exception.BaseException;

public interface ResponsibilityDispatcher {

  ResponsibilityResponse createEntity(ResponsibilityCreateForm createForm, Locale locale)
      throws BaseException;

  void deleteEntity(String userId, String roleId, Locale locale) throws BaseException;

}
