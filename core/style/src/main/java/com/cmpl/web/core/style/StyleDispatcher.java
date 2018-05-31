package com.cmpl.web.core.style;

import java.util.Locale;

public interface StyleDispatcher {

  StyleResponse updateEntity(StyleForm form, Locale locale);

}
