package com.cmpl.web.core.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class PageValidatorImpl extends BaseValidator implements PageValidator {

  public PageValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(PageCreateForm form, Locale locale) {
    return validate(form.getName(), form.getMenuTitle(), locale);
  }

  @Override
  public Error validateUpdate(PageUpdateForm form, Locale locale) {
    return validate(form.getName(), form.getMenuTitle(), locale);
  }

  Error validate(String name, String menuTitle, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_NAME, locale));
    }
    if (!isStringValid(menuTitle)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_PAGE_MENU_TITLE, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }

    return null;
  }

}
