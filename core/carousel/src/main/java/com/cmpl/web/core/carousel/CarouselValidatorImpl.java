package com.cmpl.web.core.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.validator.BaseValidator;

public class CarouselValidatorImpl extends BaseValidator implements CarouselValidator {

  public CarouselValidatorImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public Error validateCreate(CarouselCreateForm form, Locale locale) {
    return validateCarousel(form.getName(), locale);
  }

  @Override
  public Error validateUpdate(CarouselUpdateForm form, Locale locale) {
    return validateCarousel(form.getName(), locale);
  }

  Error validateCarousel(String name, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(name)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_NAME, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  @Override
  public Error validateCreate(CarouselItemCreateForm form, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(form.getCarouselId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_ID, locale));
    }
    if (!isStringValid(form.getMediaId())) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_MEDIA_ID, locale));
    }

    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

  @Override
  public Error validateDelete(String carouselItemId, Locale locale) {
    List<ErrorCause> causes = new ArrayList<>();
    if (!isStringValid(carouselItemId)) {
      causes.add(computeCause(ERROR_CAUSE.EMPTY_CAROUSEL_ITEM_ID, locale));
    }
    if (!CollectionUtils.isEmpty(causes)) {
      return computeError(causes);
    }
    return null;
  }

}
