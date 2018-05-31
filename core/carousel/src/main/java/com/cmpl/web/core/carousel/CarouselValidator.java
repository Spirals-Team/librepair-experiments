package com.cmpl.web.core.carousel;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;

public interface CarouselValidator {

  Error validateCreate(CarouselCreateForm form, Locale locale);

  Error validateCreate(CarouselItemCreateForm form, Locale locale);

  Error validateUpdate(CarouselUpdateForm form, Locale locale);

  Error validateDelete(String carouselItemId, Locale locale);
}
