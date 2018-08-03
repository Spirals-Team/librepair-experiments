package com.cmpl.web.manager.ui.core.webmastering.menu;

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
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactory;
import com.cmpl.web.core.menu.MenuCreateForm;
import com.cmpl.web.core.menu.MenuDispatcher;
import com.cmpl.web.core.menu.MenuResponse;
import com.cmpl.web.core.menu.MenuUpdateForm;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/menus")
public class MenuManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MenuManagerController.class);

  private final MenuDispatcher dispatcher;
  private final MenuManagerDisplayFactory displayFactory;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public MenuManagerController(MenuDispatcher dispatcher, MenuManagerDisplayFactory displayFactory,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.displayFactory = Objects.requireNonNull(displayFactory);
    this.messageSource = Objects.requireNonNull(messageSource);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:menu:read')")
  public ModelAndView printViewMenus(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_VIEW.name());
    return displayFactory.computeModelAndViewForViewAllMenus(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:menu:create')")
  public ModelAndView printCreateMenu(Locale locale) {
    LOGGER.info("Accès à la page de création des menus");
    return displayFactory.computeModelAndViewForCreateMenu(locale);
  }

  @GetMapping(value = "/{menuId}")
  @PreAuthorize("hasAuthority('webmastering:menu:read')")
  public ModelAndView printViewUpdateMenu(@PathVariable(value = "menuId") String menuId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_UPDATE.name() + " pour " + menuId);
    return displayFactory.computeModelAndViewForUpdateMenu(locale, menuId);
  }

  @PutMapping(value = "/{menuId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:menu:write')")
  public ResponseEntity<MenuResponse> updateMenu(@Valid @RequestBody MenuUpdateForm updateForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de modification d'un menu");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la modification de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      MenuResponse response = dispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getMenu().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:menu:create')")
  public ResponseEntity<MenuResponse> createMenu(@Valid @RequestBody MenuCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de creation d'un menu");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      MenuResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée créee, id " + response.getMenu().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la création de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{menuId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:menu:delete')")
  public ResponseEntity<MenuResponse> deleteMenu(@PathVariable(value = "menuId") String menuId, Locale locale) {

    LOGGER.info("Tentative de suppression d'un menu");

    try {
      MenuResponse response = dispatcher.deleteEntity(menuId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Menu " + menuId + " supprimée");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du menu " + menuId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping(value = "/{menuId}/_main")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView printUpdateMenuMain(@PathVariable(value = "menuId") String menuId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_UPDATE.name());
    return displayFactory.computeModelAndViewForUpdateMenuMain(menuId);
  }

  @GetMapping(value = "/{menuId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView printUpdateMenuMembership(@PathVariable(value = "menuId") String menuId) {
    LOGGER.info("Accès à la page " + BACK_PAGE.MENUS_UPDATE.name());
    return displayFactory.computeModelAndViewForMembership(menuId);
  }
}
