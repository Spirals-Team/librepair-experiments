package com.cmpl.web.core.menu;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface MenuValidator {

  Error validateCreate(MenuCreateForm form, Locale locale);

  Error validateUpdate(MenuUpdateForm form, Locale locale);
}
