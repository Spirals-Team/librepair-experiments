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
public class UserBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb userBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_VIEW).build();
  }

  @Bean
  public BreadCrumb userUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_UPDATE).build();
  }

  @Bean
  public BreadCrumb userCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_CREATE).build();
  }

  List<BreadCrumbItem> userBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.users.title").href("/manager/users").build());
    return items;
  }
}
