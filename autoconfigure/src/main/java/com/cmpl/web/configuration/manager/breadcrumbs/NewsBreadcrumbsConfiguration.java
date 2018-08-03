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
public class NewsBreadcrumbsConfiguration {

  @Bean
  public BreadCrumb newsViewBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_VIEW).build();
  }

  @Bean
  public BreadCrumb newsEditBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsUpdateBreadCrumbItems()).page(BACK_PAGE.NEWS_UPDATE).build();
  }

  @Bean
  public BreadCrumb newsCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsCreateBreadCrumbItems()).page(BACK_PAGE.NEWS_CREATE).build();
  }

  List<BreadCrumbItem> newsBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("/manager/news").build());
    return items;
  }

  List<BreadCrumbItem> newsUpdateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("/manager/news").build());
    items.add(BreadCrumbItemBuilder.create().text("news.update.title").href("#").build());
    return items;
  }

  List<BreadCrumbItem> newsCreateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("/manager/news").build());
    items.add(BreadCrumbItemBuilder.create().text("news.create.title").href("#").build());
    return items;
  }
}
