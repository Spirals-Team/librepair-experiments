package com.cmpl.web.core.factory;

import java.util.Locale;

import com.cmpl.web.core.common.message.WebMessageSource;

/**
 * Implementation de l'nterface commune de factory pour les pages
 * 
 * @author Louis
 *
 */
public class BaseDisplayFactoryImpl extends BaseFactoryImpl implements BaseDisplayFactory {

  protected BaseDisplayFactoryImpl(WebMessageSource messageSource) {
    super(messageSource);
  }

  @Override
  public String computeTileName(String tileName, Locale locale) {
    return getI18nValue(tileName, locale);
  }

  @Override
  public String computeHiddenLink(Locale locale) {
    return getI18nValue("back.index.href", locale);
  }

  @Override
  public String computeDecoratorBackTileName(Locale locale) {
    return getI18nValue("decorator.back", locale);
  }
}
