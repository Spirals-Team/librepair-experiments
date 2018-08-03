package com.cmpl.core.events_listeners;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.springframework.context.event.EventListener;

import com.cmpl.web.core.common.event.DeletedEvent;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.BaseEntity;
import com.cmpl.web.core.models.Widget;
import com.cmpl.web.core.widget.page.WidgetPageService;

public class WidgetEventsListeners {

  private final WidgetPageService widgetPageService;
  private final FileService fileService;
  private final Set<Locale> locales;

  private static final String WIDGET_PREFIX = "widget_";
  private static final String HTML_SUFFIX = ".html";
  private static final String LOCALE_CODE_PREFIX = "_";

  public WidgetEventsListeners(WidgetPageService widgetPageService, FileService fileService, Set<Locale> locales) {
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.locales = Objects.requireNonNull(locales);
    this.fileService = Objects.requireNonNull(fileService);
  }

  @EventListener
  public void handleEntityDeletion(DeletedEvent deletedEvent) {
    Class<? extends BaseEntity> clazz = deletedEvent.getEntity().getClass();
    if (Widget.class.equals(clazz)) {
      Widget deletedWidget = (Widget) deletedEvent.getEntity();

      if (deletedWidget != null) {
        widgetPageService.findByWidgetId(String.valueOf(deletedWidget.getId()))
            .forEach(widgetPageDTO -> widgetPageService.deleteEntity(widgetPageDTO.getId()));
        locales.forEach(locale -> {
          String fileName = WIDGET_PREFIX + deletedWidget.getName() + LOCALE_CODE_PREFIX + locale.getLanguage()
              + HTML_SUFFIX;
          fileService.removeFileFromSystem(fileName);
        });
      }
    }

  }
}
