package com.cmpl.web.manager.ui.core.webmastering.style;

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
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.StyleCreateForm;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.style.StyleResponse;
import com.cmpl.web.core.style.StyleUpdateForm;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/styles")
public class StyleManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(StyleManagerController.class);

  private final StyleDisplayFactory displayFactory;
  private final StyleDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public StyleManagerController(StyleDisplayFactory displayFactory, StyleDispatcher dispatcher,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {

    this.displayFactory = Objects.requireNonNull(displayFactory);
    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printViewStyles(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());

    return displayFactory.computeModelAndViewForViewAllStyles(locale, computePageNumberFromRequest(pageNumber));
  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('administration:groups:create')")
  public ModelAndView printCreateStyle(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_CREATE.name());
    return displayFactory.computeModelAndViewForCreateStyle(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('administration:groups:create')")
  public ResponseEntity<StyleResponse> createStyle(@Valid @RequestBody StyleCreateForm createForm,
      BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'un group");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      StyleResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getStyle().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(value = "/{styleId}")
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printEditStyle(@PathVariable(value = "styleId") String styleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());
    return displayFactory.computeModelAndViewForUpdateStyle(locale, styleId);
  }

  @PutMapping(value = "/{styleId}", produces = "application/json")
  @PreAuthorize("hasAuthority('webmastering:style:write')")
  public ResponseEntity<StyleResponse> handleEditStyle(@RequestBody StyleUpdateForm form, Locale locale) {
    LOGGER.info("Tentative de modification du style global");
    try {
      StyleResponse response = dispatcher.updateEntity(form, locale);

      LOGGER.info("Style modifié : " + response.getStyle().getName());
      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification du style global", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping(value = "/{styleId}/_main")
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printEditStyleMain(@PathVariable(value = "styleId") String styleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_UPDATE.name());
    return displayFactory.computeModelAndViewForUpdateStyleMain(locale, styleId);
  }

  @GetMapping(value = "/{styleId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printEditStyleGroups(@PathVariable(value = "styleId") String styleId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_UPDATE.name());
    return displayFactory.computeModelAndViewForMembership(styleId);
  }

  @DeleteMapping(value = "/{styleId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:style:delete')")
  public ResponseEntity<BaseResponse> deleteStyle(@PathVariable(value = "styleId") String styleId, Locale locale) {
    LOGGER.info("Tentative de suppression d'un style");

    try {
      BaseResponse response = dispatcher.deleteEntity(styleId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Style " + styleId + " supprimé");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du style " + styleId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }
}
