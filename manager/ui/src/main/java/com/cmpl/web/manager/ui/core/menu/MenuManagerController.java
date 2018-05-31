package com.cmpl.web.manager.ui.core.menu;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

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
    this.dispatcher = dispatcher;
    this.displayFactory = displayFactory;
    this.messageSource = messageSource;
    this.notificationCenter = notificationCenter;
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
  public ResponseEntity<MenuResponse> updateMenu(@RequestBody MenuUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'un menu");
    try {
      MenuResponse response = dispatcher.updateEntity(updateForm, locale);
      if (response.getMenu() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getMenu().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }
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
  public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuCreateForm createForm, Locale locale) {

    LOGGER.info("Tentative de modification d'un menu");
    try {
      MenuResponse response = dispatcher.createEntity(createForm, locale);
      if (response.getMenu() != null) {
        LOGGER.info("Entrée créee, id " + response.getMenu().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }
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
}
