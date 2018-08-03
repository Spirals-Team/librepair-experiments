package com.cmpl.web.manager.ui.core.administration.memberships;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.membership.MembershipCreateForm;
import com.cmpl.web.core.membership.MembershipDispatcher;
import com.cmpl.web.core.membership.MembershipResponse;
import com.cmpl.web.core.responsibility.ResponsibilityResponse;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/memberships")
public class MembershipManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipManagerController.class);

  private final MembershipDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public MembershipManagerController(MembershipDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:memberships:create')")
  public ResponseEntity<MembershipResponse> createMembership(@Valid @RequestBody MembershipCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'une association user/role");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      MembershipResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getMembership().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{entityId}/{groupId}", produces = "application/json")
  @PreAuthorize("hasAuthority('administration:memberships:delete')")
  public ResponseEntity<ResponsibilityResponse> deleteMembership(@PathVariable(name = "entityId") String entityId,
      @PathVariable(name = "groupId") String groupId, Locale locale) {
    LOGGER.info("Tentative de suppression d'une association entity groupe");

    try {
      dispatcher.deleteEntity(entityId, groupId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Association entre l'entité " + entityId + " et le groupe " + groupId + " supprimée");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'association user/role pour  l'association entre l'entité d'id "
          + entityId + " et le groupe d'id " + groupId, e);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
