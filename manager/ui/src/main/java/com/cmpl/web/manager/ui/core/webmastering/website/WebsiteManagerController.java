package com.cmpl.web.manager.ui.core.webmastering.website;

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
import com.cmpl.web.core.factory.website.WebsiteManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.website.WebsiteCreateForm;
import com.cmpl.web.core.website.WebsiteDispatcher;
import com.cmpl.web.core.website.WebsiteResponse;
import com.cmpl.web.core.website.WebsiteUpdateForm;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/websites")
public class WebsiteManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteManagerController.class);

  private final WebsiteDispatcher websiteDispatcher;
  private final WebsiteManagerDisplayFactory websiteManagerDisplayFactory;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public WebsiteManagerController(WebsiteDispatcher websiteDispatcher,
      WebsiteManagerDisplayFactory websiteManagerDisplayFactory, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.websiteDispatcher = Objects.requireNonNull(websiteDispatcher);
    this.websiteManagerDisplayFactory = Objects.requireNonNull(websiteManagerDisplayFactory);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewWebsites(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.WEBSITE_VIEW.name());
    return websiteManagerDisplayFactory.computeModelAndViewForViewAllWebsites(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:websites:create')")
  public ModelAndView printCreateWebsite(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WEBSITE_CREATE.name());
    return websiteManagerDisplayFactory.computeModelAndViewForCreateWebsite(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:websites:create')")
  public ResponseEntity<WebsiteResponse> createWebsite(@Valid @RequestBody WebsiteCreateForm createForm,
      BindingResult bindingResult, Locale locale) {
    LOGGER.info("Tentative de création d'un website");

    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      WebsiteResponse response = websiteDispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getWebsite().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @PutMapping(value = "/{websiteId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:websites:write')")
  public ResponseEntity<WebsiteResponse> updateWebsite(@Valid @RequestBody WebsiteUpdateForm updateForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de modification d'un website");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la modification de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      WebsiteResponse response = websiteDispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getWebsite().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{websiteId}")
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewUpdateWebsite(@PathVariable(value = "websiteId") String websiteId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WEBSITE_UPDATE.name() + " pour " + websiteId);
    return websiteManagerDisplayFactory.computeModelAndViewForUpdateWebsite(locale, websiteId);
  }

  @GetMapping(value = "/{websiteId}/_main")
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewUpdateWebsiteMain(@PathVariable(value = "websiteId") String websiteId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WEBSITE_UPDATE.name() + " pour " + websiteId + " pour la partie main");
    return websiteManagerDisplayFactory.computeModelAndViewForUpdateWebsiteMain(locale, websiteId);
  }

  @DeleteMapping(value = "/{websiteId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:websites:delete')")
  public ResponseEntity<BaseResponse> deleteWebsite(@PathVariable(value = "websiteId") String websiteId,
      Locale locale) {
    LOGGER.info("Tentative de suppression d'un websitee");

    try {
      BaseResponse response = websiteDispatcher.deleteEntity(websiteId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Websitee " + websiteId + " supprimé");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du website " + websiteId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/{websiteId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewUpdateWebsiteMemberships(@PathVariable(value = "websiteId") String websiteId) {
    LOGGER.info(
        "Accès à la page " + BACK_PAGE.WEBSITE_UPDATE.name() + " pour " + websiteId + " pour la partie memberships");
    return websiteManagerDisplayFactory.computeModelAndViewForMembership(websiteId);
  }

  @GetMapping(value = "/{websiteId}/_sitemap")
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewUpdateWebsiteSitemap(@PathVariable(value = "websiteId") String websiteId,
      Locale locale) {
    LOGGER
        .info("Accès à la page " + BACK_PAGE.WEBSITE_UPDATE.name() + " pour " + websiteId + " pour la partie sitemap");
    return websiteManagerDisplayFactory.computeModelAndViewForUpdateWebsiteSitemap(locale, websiteId);
  }

  @GetMapping(value = "/{websiteId}/_design")
  @PreAuthorize("hasAuthority('webmastering:websites:read')")
  public ModelAndView printViewUpdateWebsiteDesign(@PathVariable(value = "websiteId") String websiteId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WEBSITE_UPDATE.name() + " pour " + websiteId + " pour la partie design");
    return websiteManagerDisplayFactory.computeModelAndViewForUpdateWebsiteDesign(locale, websiteId);
  }

}
