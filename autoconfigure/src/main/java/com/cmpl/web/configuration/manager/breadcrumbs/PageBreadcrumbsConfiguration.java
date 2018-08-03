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
public class PageBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb pageBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_VIEW).build();
  }

  @Bean
  public BreadCrumb pageUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_UPDATE).build();
  }

  @Bean
  public BreadCrumb pageCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(pageBreadCrumbItems()).page(BACK_PAGE.PAGES_CREATE).build();
  }

  List<BreadCrumbItem> pageBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.pages.title").href("/manager/pages").build());
    return items;
  }
}
