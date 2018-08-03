package com.cmpl.web.core.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.builder.Builder;

public class BackMenuBuilder extends Builder<BackMenu> {

  private final PluginRegistry<BackMenuItem, String> registry;

  private BackMenuBuilder(PluginRegistry<BackMenuItem, String> registry) {
    this.registry = registry;
  }

  private List<BackMenuItem> items;

  public BackMenuBuilder items(List<BackMenuItem> items) {
    this.items = items;
    return this;
  }

  public BackMenuBuilder item(BackMenuItem item) {
    if (CollectionUtils.isEmpty(items)) {
      items = new ArrayList<>();
    }
    if (!items.contains(item)) {
      items.add(item);
    }

    return this;
  }

  @Override
  public BackMenu build() {
    return new BackMenu(registry);
  }

  public static BackMenuBuilder create(PluginRegistry<BackMenuItem, String> registry) {
    return new BackMenuBuilder(registry);
  }

}
