package com.cmpl.web.configuration.core.breadcrumb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbPlugin;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EnablePluginRegistries({BreadCrumbPlugin.class})
public class BreadCrumbConfiguration { 

  @Autowired
  @Qualifier(value = "breadCrumbs")
  private PluginRegistry<BreadCrumb, BACK_PAGE> registry;

}
