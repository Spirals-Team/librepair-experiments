package com.cmpl.web.configuration.core.widget;

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

import com.cmpl.core.events_listeners.WidgetEventsListeners;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.Widget;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.core.widget.WidgetDispatcherImpl;
import com.cmpl.web.core.widget.WidgetPage;
import com.cmpl.web.core.widget.WidgetPageRepository;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetPageServiceImpl;
import com.cmpl.web.core.widget.WidgetRepository;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.WidgetServiceImpl;
import com.cmpl.web.core.widget.WidgetTranslator;
import com.cmpl.web.core.widget.WidgetTranslatorImpl;
import com.cmpl.web.core.widget.WidgetValidator;
import com.cmpl.web.core.widget.WidgetValidatorImpl;

@Configuration
@EntityScan(basePackageClasses = {Widget.class, WidgetPage.class})
@EnableJpaRepositories(basePackageClasses = {WidgetRepository.class, WidgetPageRepository.class})
public class WidgetConfiguration {

  @Bean
  BackMenuItem widgetBackMenuItem(BackMenuItem webmastering, Privilege widgetsReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.widgets.href").label("back.widgets.label")
        .title("back.widgets.title").iconClass("fa fa-cube").parent(webmastering).order(8)
        .privilege(widgetsReadPrivilege.privilege()).build();
  }

  @Bean
  BreadCrumb widgetBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_VIEW).build();
  }

  @Bean
  BreadCrumb widgetUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_UPDATE).build();
  }

  @Bean
  BreadCrumb widgetCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(widgetBreadCrumbItems()).page(BACK_PAGE.WIDGET_CREATE).build();
  }

  List<BreadCrumbItem> widgetBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.widgets.title").href("back.widgets.href").build());
    return items;
  }

  @Bean
  WidgetService widgetService(ApplicationEventPublisher publisher, WidgetRepository widgetRepository,
      FileService fileService) {
    return new WidgetServiceImpl(publisher, widgetRepository, fileService);
  }

  @Bean
  WidgetPageService widgetPageService(ApplicationEventPublisher publisher, WidgetPageRepository widgetPageRepository) {
    return new WidgetPageServiceImpl(publisher, widgetPageRepository);
  }

  @Bean
  WidgetManagerDisplayFactory widgetManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      WidgetService widgetService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      PluginRegistry<WidgetProviderPlugin, String> widgetProviders, Set<Locale> availableLocales) {
    return new WidgetManagerDisplayFactoryImpl(menuFactory, messageSource, contextHolder, widgetService, breadCrumbs,
        widgetProviders, availableLocales);
  }

  @Bean
  WidgetTranslator widgetTranslator() {
    return new WidgetTranslatorImpl();
  }

  @Bean
  WidgetValidator widgetValidator(WebMessageSource messageSource) {
    return new WidgetValidatorImpl(messageSource);
  }

  @Bean
  WidgetDispatcher widgetDispatcher(WidgetService widgetService, WidgetPageService widgetPageService,
      WidgetValidator widgetValidator, WidgetTranslator widgetTranslator) {
    return new WidgetDispatcherImpl(widgetTranslator, widgetValidator, widgetService, widgetPageService);
  }

  @Bean
  WidgetEventsListeners widgetEventsListener(WidgetPageService widgetPageService, FileService fileService,
      Set<Locale> availableLocales) {
    return new WidgetEventsListeners(widgetPageService, fileService, availableLocales);
  }

}
