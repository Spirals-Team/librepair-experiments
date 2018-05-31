package com.cmpl.web.core.widget;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface WidgetValidator {

  Error validateCreate(WidgetCreateForm form, Locale locale);

  Error validateUpdate(WidgetUpdateForm form, Locale locale);

  Error validateCreate(WidgetPageCreateForm form, Locale locale);

  Error validateDelete(String id, Locale locale);

}
