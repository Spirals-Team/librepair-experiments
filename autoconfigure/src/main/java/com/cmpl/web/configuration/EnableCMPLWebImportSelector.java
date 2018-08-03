package com.cmpl.web.configuration;

import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;

public class EnableCMPLWebImportSelector extends AutoConfigurationImportSelector {

  @Override
  protected Class<?> getSpringFactoriesLoaderFactoryClass() {
    return EnableCMPLWeb.class;
  }

  @Override
  protected Class<?> getAnnotationClass() {
    return EnableCMPLWeb.class;
  }
}
