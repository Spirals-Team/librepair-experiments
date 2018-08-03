package com.cmpl.web.manager.ui.core.administration.group;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.common.resource.BaseResponse;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactory;
import com.cmpl.web.core.group.GroupCreateForm;
import com.cmpl.web.core.group.GroupDispatcher;
import com.cmpl.web.core.group.GroupResponse;
import com.cmpl.web.core.group.GroupUpdateForm;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/groups")
public class GroupManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GroupManagerController.class);

  private final GroupDispatcher groupDispatcher;
  private final GroupManagerDisplayFactory groupManagerDisplayFactory;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public GroupManagerController(GroupDispatcher groupDispatcher, GroupManagerDisplayFactory groupManagerDisplayFactory,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {

    this.groupDispatcher = Objects.requireNonNull(groupDispatcher);
    this.groupManagerDisplayFactory = Objects.requireNonNull(groupManagerDisplayFactory);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('administration:groups:read')")
  public ModelAndView printViewGroups(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.GROUP_VIEW.name());
    return groupManagerDisplayFactory.computeModelAndViewForViewAllGroups(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:groups:create')")
  public ModelAndView printCreateGroup(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.GROUP_CREATE.name());
    return groupManagerDisplayFactory.computeModelAndViewForCreateGroup(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:groups:create')")
  public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupCreateForm createForm,
      BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'un group");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      GroupResponse response = groupDispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getGroup().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{groupId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:groups:write')")
  public ResponseEntity<GroupResponse> updateGroup(@Valid @RequestBody GroupUpdateForm updateForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de modification d'un group");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la modification de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      GroupResponse response = groupDispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getGroup().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{groupId}")
  @PreAuthorize("hasAuthority('administration:groups:read')")
  public ModelAndView printViewUpdateGroup(@PathVariable(value = "groupId") String groupId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.GROUP_UPDATE.name() + " pour " + groupId);
    return groupManagerDisplayFactory.computeModelAndViewForUpdateGroup(locale, groupId);
  }

  @GetMapping(value = "/{groupId}/_main")
  @PreAuthorize("hasAuthority('administration:groups:read')")
  public ModelAndView printViewUpdateGroupMain(@PathVariable(value = "groupId") String groupId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.GROUP_UPDATE.name() + " pour " + groupId + " pour la partie main");
    return groupManagerDisplayFactory.computeModelAndViewForUpdateGroupMain(locale, groupId);
  }

  @DeleteMapping(value = "/{groupId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:groups:delete')")
  public ResponseEntity<BaseResponse> deleteGroup(@PathVariable(value = "groupId") String groupId, Locale locale) {
    LOGGER.info("Tentative de suppression d'un groupe");

    try {
      BaseResponse response = groupDispatcher.deleteEntity(groupId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Groupe " + groupId + " supprimé");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du group " + groupId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/{groupId}/_memberships")
  @PreAuthorize("hasAuthority('administration:groups:read')")
  public ModelAndView printViewUpdateGroupMemberships(@PathVariable(value = "groupId") String groupId) {
    LOGGER
        .info("Accès à la page " + BACK_PAGE.GROUP_UPDATE.name() + " pour " + groupId + " pour la partie memberships");
    return groupManagerDisplayFactory.computeModelAndViewForMembership(groupId);
  }

}
