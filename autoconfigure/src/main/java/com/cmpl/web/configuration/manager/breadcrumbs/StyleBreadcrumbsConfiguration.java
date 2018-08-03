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
public class StyleBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb styleBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_VIEW).build();
  }

  @Bean
  public BreadCrumb styleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_UPDATE).build();
  }

  @Bean
  public BreadCrumb styleCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_CREATE).build();
  }

  List<BreadCrumbItem> styleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.style.title").href("/manager/styles").build());
    return items;
  }
}
