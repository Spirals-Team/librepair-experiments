package com.cmpl.web.core.style;

import java.util.Locale;

public interface StyleDispatcher {

  StyleResponse updateEntity(StyleUpdateForm form, Locale locale);

  StyleResponse createEntity(StyleCreateForm form, Locale locale);

  StyleResponse deleteEntity(String styleId, Locale locale);

}
