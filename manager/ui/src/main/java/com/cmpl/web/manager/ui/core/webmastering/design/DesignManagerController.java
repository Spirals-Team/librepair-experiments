package com.cmpl.web.manager.ui.core.webmastering.design;

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

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.design.DesignCreateForm;
import com.cmpl.web.core.design.DesignDispatcher;
import com.cmpl.web.core.design.DesignResponse;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
public class DesignManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DesignManagerController.class);

  private final DesignDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public DesignManagerController(DesignDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @PostMapping(value = "/manager/designs", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:designs:create')")
  public ResponseEntity<DesignResponse> createDesign(@Valid @RequestBody DesignCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'une association website/style");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      DesignResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getDesignDTO().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/designs/{websiteId}/{styleId}", produces = "application/json")
  @PreAuthorize("hasAuthority('webmastering:designs:delete')")
  public ResponseEntity<DesignResponse> deleteDesign(@PathVariable(name = "websiteId") String websiteId,
      @PathVariable(name = "styleId") String styleId, Locale locale) {
    LOGGER.info("Tentative de suppression d'une association website style");

    try {
      dispatcher.deleteEntity(Long.parseLong(websiteId), Long.parseLong(styleId));
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Association entre le website " + websiteId + " et le style " + styleId + " supprimée");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'association website/style pour  l'association entre l'website d'id "
          + websiteId + " et le style d'id " + styleId, e);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
