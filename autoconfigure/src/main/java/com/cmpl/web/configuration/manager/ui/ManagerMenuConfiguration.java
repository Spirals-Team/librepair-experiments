package com.cmpl.web.configuration.manager.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;

@Configuration
public class ManagerMenuConfiguration {

  @Bean
  public BackMenuItem groupBackMenuItem(BackMenuItem administration,
      com.cmpl.web.core.common.user.Privilege groupsReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/groups").label("back.groups.label").title("back.groups.title")
        .iconClass("fa fa-building").parent(administration).order(1).privilege(groupsReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem mediasBackMenuItem(BackMenuItem webmastering, Privilege mediaReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/medias").label("back.medias.label").title("back.medias.title")
        .order(4).iconClass("fa fa-file-image-o").parent(webmastering).privilege(mediaReadPrivilege.privilege())
        .build();
  }

  @Bean
  public BackMenuItem newsBackMenuItem(BackMenuItem webmastering, Privilege newsReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/news").label("back.news.label").title("back.news.title").order(6)
        .iconClass("fa fa-newspaper-o").parent(webmastering).privilege(newsReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem pagesBackMenuItem(BackMenuItem webmastering, Privilege pagesReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/pages").label("back.pages.label").title("back.pages.title")
        .order(1).iconClass("fa fa-code").parent(webmastering).privilege(pagesReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem roleBackMenuItem(BackMenuItem administration,
      com.cmpl.web.core.common.user.Privilege rolesReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/roles").label("back.roles.label").title("back.roles.title")
        .iconClass("fa fa-tasks").parent(administration).order(1).privilege(rolesReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem styleBackMenuItem(BackMenuItem webmastering, Privilege styleReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/styles").label("back.style.label").title("back.style.title")
        .order(5).iconClass("fa fa-css3").parent(webmastering).privilege(styleReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem userBackMenuItem(BackMenuItem administration, Privilege usersReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/users").label("back.users.label").title("back.users.title")
        .iconClass("fa fa-user").parent(administration).privilege(usersReadPrivilege.privilege()).order(0).build();
  }

  @Bean
  public BackMenuItem indexBackMenuItem(Privilege indexReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/").label("back.index.label").title("back.index.title")
        .iconClass("fa fa-home").order(0).privilege(indexReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem administration(Privilege administrationReadPrivilege) {
    return BackMenuItemBuilder.create().href("#").label("back.administration.label").title("back.administration.title")
        .iconClass("fa fa-id-badge").order(1).privilege(administrationReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem webmastering(Privilege webmasteringReadPrivilege) {
    return BackMenuItemBuilder.create().href("#").label("back.webmastering.label").title("back.webmastering.title")
        .iconClass("fa fa-sitemap").order(2).privilege(webmasteringReadPrivilege.privilege()).build();
  }

  @Bean
  public BackMenuItem websitesBackMenuItem(BackMenuItem webmastering, Privilege websitesReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/websites").label("back.websites.label")
        .title("back.websites.title").order(6).iconClass("fa fa-sitemap").parent(webmastering)
        .privilege(websitesReadPrivilege.privilege()).build();
  }

}
