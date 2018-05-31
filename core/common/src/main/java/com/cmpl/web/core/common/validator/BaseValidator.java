package com.cmpl.web.core.common.validator;

import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.ERROR_TYPE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorBuilder;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.error.ErrorCauseBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;

public class BaseValidator {

  private final WebMessageSource messageSource;

  public BaseValidator(WebMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public boolean isStringValid(String stringToValidate) {
    return StringUtils.hasText(stringToValidate);
  }

  public ErrorCause computeCause(ERROR_CAUSE errorCause, Locale locale) {
    return ErrorCauseBuilder
        .create().code(errorCause.toString()).message(getI18n(errorCause.getCauseKey(), locale))
        .build();
  }

  public Error computeError(List<ErrorCause> causes) {
    return ErrorBuilder.create().code(ERROR_TYPE.INVALID_REQUEST.toString()).causes(causes).build();
  }

  protected String getI18n(String key, Locale locale) {
    return messageSource.getMessage(key, null, locale);
  }

}
