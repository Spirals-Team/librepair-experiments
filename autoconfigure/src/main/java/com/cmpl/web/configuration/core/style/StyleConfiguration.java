package com.cmpl.web.configuration.core.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.Style;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.style.StyleDispatcherImpl;
import com.cmpl.web.core.style.StyleRepository;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.style.StyleServiceImpl;
import com.cmpl.web.core.style.StyleTranslator;
import com.cmpl.web.core.style.StyleTranslatorImpl;

@Configuration
@EntityScan(basePackageClasses = Style.class)
@EnableJpaRepositories(basePackageClasses = StyleRepository.class)
public class StyleConfiguration {

  @Bean
  public StyleService styleService(ApplicationEventPublisher publisher, StyleRepository styleRepository,
      MediaService mediaService, FileService fileService) {
    return new StyleServiceImpl(publisher, styleRepository, mediaService, fileService);
  }

  @Bean
  public StyleDispatcher styleDispacther(StyleService styleService, StyleTranslator styleTranslator) {
    return new StyleDispatcherImpl(styleService, styleTranslator);
  }

  @Bean
  BackMenuItem styleBackMenuItem(BackMenuItem webmastering, Privilege styleReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.style.href").label("back.style.label").title("back.style.title")
        .order(5).iconClass("fa fa-css3").parent(webmastering).privilege(styleReadPrivilege.privilege()).build();
  }

  @Bean
  BreadCrumb styleBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_VIEW).build();
  }

  @Bean
  BreadCrumb styleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(styleBreadCrumbItems()).page(BACK_PAGE.STYLES_UPDATE).build();
  }

  List<BreadCrumbItem> styleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.style.title").href("back.style.href").build());
    return items;
  }

  @Bean
  public StyleTranslator styleTranslator() {
    return new StyleTranslatorImpl();
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      StyleService styleService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales) {
    return new StyleDisplayFactoryImpl(menuFactory, messageSource, styleService, contextHolder, breadCrumbs,
        availableLocales);
  }
}
