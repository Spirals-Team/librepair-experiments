package com.cmpl.web.core.widget;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.models.WidgetPage;
import com.cmpl.web.core.widget.page.WidgetPageDAO;
import com.cmpl.web.core.widget.page.WidgetPageDAOImpl;
import com.cmpl.web.core.widget.page.WidgetPageMapper;
import com.cmpl.web.core.widget.page.WidgetPageRepository;
import com.cmpl.web.core.widget.page.WidgetPageService;
import com.cmpl.web.core.widget.page.WidgetPageServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {Widget.class, WidgetPage.class})
@EnableJpaRepositories(basePackageClasses = {WidgetRepository.class, WidgetPageRepository.class})
public class WidgetConfiguration {

  @Bean
  public WidgetDAO widgetDAO(WidgetRepository widgetRepository, ApplicationEventPublisher publisher) {
    return new WidgetDAOImpl(widgetRepository, publisher);
  }

  @Bean
  public WidgetMapper widgetMapper() {
    return new WidgetMapper();
  }

  @Bean
  public BackMenuItem widgetBackMenuItem(BackMenuItem webmastering, Privilege widgetsReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/widgets").label("back.widgets.label").title("back.widgets.title")
        .iconClass("fa fa-cube").parent(webmastering).order(8).privilege(widgetsReadPrivilege.privilege()).build();
  }

  @Bean
  public WidgetService widgetService(WidgetDAO widgetDAO, WidgetMapper widgetMapper, FileService fileService) {
    return new WidgetServiceImpl(widgetDAO, widgetMapper, fileService);
  }

  @Bean
  public WidgetPageMapper widgetPageMapper() {
    return new WidgetPageMapper();
  }

  @Bean
  public WidgetPageDAO widgetPageDAO(WidgetPageRepository widgetPageRepository, ApplicationEventPublisher publisher) {
    return new WidgetPageDAOImpl(widgetPageRepository, publisher);
  }

  @Bean
  public WidgetPageService widgetPageService(WidgetPageDAO widgetPageDAO, WidgetPageMapper widgetPageMapper) {
    return new WidgetPageServiceImpl(widgetPageDAO, widgetPageMapper);
  }

  @Bean
  public WidgetTranslator widgetTranslator() {
    return new WidgetTranslatorImpl();
  }

  @Bean
  public WidgetDispatcher widgetDispatcher(WidgetService widgetService, WidgetPageService widgetPageService,
      WidgetTranslator widgetTranslator) {
    return new WidgetDispatcherImpl(widgetTranslator, widgetService, widgetPageService);
  }

}
