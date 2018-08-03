package com.cmpl.web.core.common.message;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Implementation de l'interface de i18n
 * 
 * @author Louis
 *
 */
public class WebMessageSourceImpl extends ResourceBundleMessageSource implements WebMessageSource {

  @Override
  public String getI18n(String code, Locale locale) {
    return getMessage(code, null, locale);
  }

  @Override
  public String getI18n(String code, Locale locale, Object... args) {
    return getMessage(code, args, locale);
  }

  @Override
  public String getMessage(String code, Locale locale, Object... args) {
    return getMessage(code, args, locale);
  }

}
