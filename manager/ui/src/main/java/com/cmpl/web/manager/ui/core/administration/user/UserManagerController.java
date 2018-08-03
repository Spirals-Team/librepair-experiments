package com.cmpl.web.manager.ui.core.administration.user;

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
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.user.UserCreateForm;
import com.cmpl.web.core.user.UserDispatcher;
import com.cmpl.web.core.user.UserResponse;
import com.cmpl.web.core.user.UserUpdateForm;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/users")
public class UserManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerController.class);

  private final UserManagerDisplayFactory userManagerDisplayFactory;
  private final UserDispatcher userDispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public UserManagerController(UserManagerDisplayFactory userManagerDisplayFactory, UserDispatcher userDispatcher,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {

    this.userManagerDisplayFactory = Objects.requireNonNull(userManagerDisplayFactory);
    this.userDispatcher = Objects.requireNonNull(userDispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUsers(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_VIEW.name());
    return userManagerDisplayFactory.computeModelAndViewForViewAllUsers(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:users:create')")
  public ModelAndView printCreateUser(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_CREATE.name());
    return userManagerDisplayFactory.computeModelAndViewForCreateUser(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:users:create')")
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateForm createForm,
      BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'un utilisateur");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      UserResponse response = userDispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getUser().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{userId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:users:write')")
  public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserUpdateForm updateForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de modification d'un utilisateur");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la modification de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      UserResponse response = userDispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getUser().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{userId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:users:delete')")
  public ResponseEntity<UserResponse> deleteUser(@PathVariable(value = "userId") String userId, Locale locale) {

    LOGGER.info("Tentative de suppression d'un utilisateur");
    try {
      UserResponse response = userDispatcher.deleteEntity(userId, locale);
      LOGGER.info("User " + userId + " supprimé");
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping(value = "/{userId}")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUser(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId);
    return userManagerDisplayFactory.computeModelAndViewForUpdateUser(locale, userId);
  }

  @GetMapping(value = "/{userId}/_main")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUserMain(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId + " pour la partie main");
    return userManagerDisplayFactory.computeModelAndViewForUpdateUserMain(locale, userId);
  }

  @GetMapping(value = "/{userId}/_roles")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUserRoles(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId + " pour la partie roles");
    return userManagerDisplayFactory.computeModelAndViewForUpdateUserRoles(locale, userId);
  }

  @GetMapping(value = "/{userId}/_memberships")
  @PreAuthorize("hasAuthority('administration:users:read')")
  public ModelAndView printViewUpdateUserMemberships(@PathVariable(value = "userId") String userId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.USER_UPDATE.name() + " pour " + userId + " pour la partie groupes");
    return userManagerDisplayFactory.computeModelAndViewForMembership(userId);
  }

}
