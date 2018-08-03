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
public class RoleBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb roleBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_VIEW).build();
  }

  @Bean
  public BreadCrumb roleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_UPDATE).build();
  }

  @Bean
  public BreadCrumb roleCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_CREATE).build();
  }

  List<BreadCrumbItem> roleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.roles.title").href("/manager/roles").build());
    return items;
  }
}
