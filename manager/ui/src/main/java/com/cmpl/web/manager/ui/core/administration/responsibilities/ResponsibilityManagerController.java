package com.cmpl.web.manager.ui.core.administration.responsibilities;

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
import com.cmpl.web.core.responsibility.ResponsibilityCreateForm;
import com.cmpl.web.core.responsibility.ResponsibilityDispatcher;
import com.cmpl.web.core.responsibility.ResponsibilityResponse;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
public class ResponsibilityManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ResponsibilityManagerController.class);

  private final ResponsibilityDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public ResponsibilityManagerController(ResponsibilityDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @PostMapping(value = "/manager/responsibilities", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:responsibilities:create')")
  public ResponseEntity<ResponsibilityResponse> createResponsibility(
      @Valid @RequestBody ResponsibilityCreateForm createForm, BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'une association user/role");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      ResponsibilityResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getResponsibilityDTO().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/responsibilities/{userId}/{roleId}", produces = "application/json")
  @PreAuthorize("hasAuthority('administration:responsibilities:delete')")
  public ResponseEntity<ResponsibilityResponse> deleteResponsibility(@PathVariable(name = "userId") String userId,
      @PathVariable(name = "roleId") String roleId, Locale locale) {
    LOGGER.info("Tentative de suppression d'une association user role");

    try {
      dispatcher.deleteEntity(userId, roleId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Association entre le user " + userId + " et le role " + roleId + " supprimée");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'association user/role pour  l'association entre l'utilisateur d'id "
          + userId + " et le role d'id " + roleId, e);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
