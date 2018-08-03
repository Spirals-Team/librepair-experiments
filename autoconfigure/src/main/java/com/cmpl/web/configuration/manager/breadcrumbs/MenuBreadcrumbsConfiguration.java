package com.cmpl.web.configuration.manager.breadcrumbs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
public class MenuBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb menuBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_VIEW).build();
  }

  @Bean
  public BreadCrumb menuUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_UPDATE).build();
  }

  @Bean
  public BreadCrumb menuCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(menuBreadCrumbItems()).page(BACK_PAGE.MENUS_CREATE).build();
  }

  List<BreadCrumbItem> menuBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.menus.title").href("/manager/menus").build());
    return items;
  }
}
