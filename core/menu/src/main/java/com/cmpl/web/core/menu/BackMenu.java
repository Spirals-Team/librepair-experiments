package com.cmpl.web.core.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.plugin.core.PluginRegistry;

public class BackMenu {

  private final PluginRegistry<BackMenuItem, String> registry;

  public BackMenu(PluginRegistry<BackMenuItem, String> registry) {
    this.registry = registry;
  }

  public List<BackMenuItem> getItems() {
    return this.registry.getPlugins().stream().sorted((e1, e2) -> Integer.compare(e1.getOrder(), e2.getOrder()))
        .collect(Collectors.toList());
  }

}
