package com.cmpl.web.configuration.core.common;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuration de la locale
 * 
 * @author Louis
 *
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

  /**
   * Declaration de la locale du projet et de la locale par defaut
   * 
   * @return
   */
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.FRANCE);
    return slr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public Set<Locale> availableLocales() {
    Set<Locale> locales = new HashSet<>();
    locales.add(Locale.FRANCE);
    locales.add(Locale.US);
    return locales;
  }

}
