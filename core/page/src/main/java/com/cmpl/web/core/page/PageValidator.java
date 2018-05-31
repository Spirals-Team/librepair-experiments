package com.cmpl.web.core.page;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface PageValidator {

  Error validateCreate(PageCreateForm form, Locale locale);

  Error validateUpdate(PageUpdateForm form, Locale locale);
}
