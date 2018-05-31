package com.cmpl.web.core.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class WidgetValidatorImpl extends BaseValidator implements WidgetValidator {

  public WidgetValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(WidgetCreateForm form, Locale locale) {
    return validate(form.getName(), form.getType(), locale);
  }

  @Override
  public Error validateUpdate(WidgetUpdateForm form, Locale locale) {
    return validate(form.getName(), form.getType(), locale);
  }

  @Override
  public Error validateCreate(WidgetPageCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getPageId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_WIDGET_PAGE_ID, locale));
    }
    if (!isStringValid(form.getWidgetId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_WIDGET_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  @Override
  public Error validateDelete(String id, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(id)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_WIDGET_PAGE_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

  Error validate(String name, String type, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_WIDGET_NAME, locale));
    }
    if (!isStringValid(type)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_WIDGET_TYPE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }
}
