package com.cmpl.web.configuration.core.index;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
public class IndexConfiguration {

  @Bean
  BreadCrumb indexBreadCrumb() {
    return BreadCrumbBuilder.create().items(indexBreadCrumbItems()).page(BACK_PAGE.INDEX).build();
  }

  List<BreadCrumbItem> indexBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    return items;
  }

  @Bean
  BackMenuItem indexBackMenuItem(Privilege indexReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.index.href").label("back.index.label").title("back.index.title")
        .iconClass("fa fa-home").order(0).privilege(indexReadPrivilege.privilege()).build();
  }

  @Bean
  BackMenuItem administration(Privilege administrationReadPrivilege) {
    return BackMenuItemBuilder.create().href("#").label("back.administration.label").title("back.administration.title")
        .iconClass("fa fa-id-badge").order(1).privilege(administrationReadPrivilege.privilege()).build();
  }

  @Bean
  BackMenuItem webmastering(Privilege webmasteringReadPrivilege) {
    return BackMenuItemBuilder.create().href("#").label("back.webmastering.label").title("back.webmastering.title")
        .iconClass("fa fa-sitemap").order(2).privilege(webmasteringReadPrivilege.privilege()).build();
  }

  @Bean
  public Privilege indexReadPrivilege() {
    return new SimplePrivilege("index", "index", "read");
  }

  @Bean
  public Privilege administrationReadPrivilege() {
    return new SimplePrivilege("administration", "administration", "read");
  }

  @Bean
  public Privilege webmasteringReadPrivilege() {
    return new SimplePrivilege("webmastering", "webmastering", "read");
  }

}
