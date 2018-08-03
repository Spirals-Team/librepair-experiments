package com.cmpl.web.core.factory;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.common.message.WebMessageSource;

/**
 * Implmentaiton de l'interface commune aux factory utilisant des cles i18n
 * 
 * @author Louis
 *
 */
public class BaseFactoryImpl implements BaseFactory {

  protected WebMessageSource messageSource;

  protected BaseFactoryImpl(WebMessageSource messageSource) {

    this.messageSource = Objects.requireNonNull(messageSource);

  }

  @Override
  public String getI18nValue(String key, Locale locale) {
    return messageSource.getI18n(key, locale);
  }

  @Override
  public String getI18nValue(String key, Locale locale, Object... args) {
    return messageSource.getI18n(key, locale, args);
  }

}
