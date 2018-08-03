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
public class CarouselBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb carouselBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_VIEW).build();
  }

  @Bean
  public BreadCrumb carouselUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_UPDATE).build();
  }

  @Bean
  public BreadCrumb carouselCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(carouselBreadCrumbItems()).page(BACK_PAGE.CAROUSELS_CREATE).build();
  }

  List<BreadCrumbItem> carouselBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.carousels.title").href("/manager/carousels").build());
    return items;
  }
}
