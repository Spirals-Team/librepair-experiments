package com.cmpl.web.manager.ui.core.webmastering.widget;

import java.util.Locale;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageResponse;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
public class WidgetPageManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetPageManagerController.class);

  private final WidgetDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public WidgetPageManagerController(WidgetDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @PostMapping(value = "/manager/pages/{pageId}/widgets", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ResponseEntity<WidgetPageResponse> createWidgetAssociation(@PathVariable(name = "pageId") String pageId,
      @Valid @RequestBody WidgetPageCreateForm createForm, BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'une association widget-page");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      WidgetPageResponse response = dispatcher.createEntity(pageId, createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getWidgetPage().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

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
