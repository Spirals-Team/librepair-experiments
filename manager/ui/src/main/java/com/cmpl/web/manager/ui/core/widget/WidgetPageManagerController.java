package com.cmpl.web.manager.ui.core.widget;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.core.widget.WidgetPageCreateForm;
import com.cmpl.web.core.widget.WidgetPageResponse;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
public class WidgetPageManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetPageManagerController.class);

  private final WidgetDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public WidgetPageManagerController(WidgetDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {
    this.dispatcher = dispatcher;
    this.notificationCenter = notificationCenter;
    this.messageSource = messageSource;
  }

  @PostMapping(value = "/manager/pages/{pageId}/widgets", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ResponseEntity<WidgetPageResponse> createWidgetAssociation(@PathVariable(name = "pageId") String pageId,
      @RequestBody WidgetPageCreateForm createForm, Locale locale) {

    LOGGER.info("Tentative de création d'une association widget-page");
    try {
      WidgetPageResponse response = dispatcher.createEntity(pageId, createForm, locale);
      if (response.getWidgetPage() != null) {
        LOGGER.info("Entrée crée, id " + response.getWidgetPage().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/pages/{pageId}/widgets/{widgetId}", produces = "application/json")
  @PreAuthorize("hasAuthority('webmastering:widgets:delete')")
  public ResponseEntity<WidgetPageResponse> deleteWidgetAssociation(@PathVariable(name = "pageId") String pageId,
      @PathVariable(name = "widgetId") String widgetId, Locale locale) {
    LOGGER.info("Tentative de suppression d'un widgetPage");
    try {
      dispatcher.deleteEntity(pageId, widgetId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
    } catch (BaseException e) {
      LOGGER.error("Echec de la suppression de l'association widget/meta " + widgetId + " pour la page " + pageId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
