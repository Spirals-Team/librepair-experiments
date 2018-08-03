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
public class WebsiteBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb websiteBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).page(BACK_PAGE.WEBSITE_VIEW).build();
  }

  @Bean
  public BreadCrumb websiteUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).page(BACK_PAGE.WEBSITE_UPDATE).build();
  }

  @Bean
  public BreadCrumb websiteCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(websiteBreadCrumbItems()).page(BACK_PAGE.WEBSITE_CREATE).build();
  }

  List<BreadCrumbItem> websiteBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.websites.title").href("/manager/websites").build());
    return items;
  }
}
