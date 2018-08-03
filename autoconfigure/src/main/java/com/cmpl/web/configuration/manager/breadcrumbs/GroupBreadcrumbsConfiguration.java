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
public class GroupBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb groupBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_VIEW).build();
  }

  @Bean
  public BreadCrumb groupUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_UPDATE).build();
  }

  @Bean
  public BreadCrumb groupCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(groupBreadCrumbItems()).page(BACK_PAGE.GROUP_CREATE).build();
  }

  List<BreadCrumbItem> groupBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.groups.title").href("/manager/groups").build());
    return items;
  }
}
