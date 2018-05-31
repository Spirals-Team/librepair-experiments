package com.cmpl.web.manager.ui.core.page;

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
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageCreateForm;
import com.cmpl.web.core.page.PageDispatcher;
import com.cmpl.web.core.page.PageResponse;
import com.cmpl.web.core.page.PageUpdateForm;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

/**
 * Controller pour la gestion des pages dans le back office
 * 
 * @author Louis
 *
 */
@ManagerController
@RequestMapping(value = "/manager/pages")
public class PageManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PageManagerController.class);

  private final PageManagerDisplayFactory pageManagerDisplayFactory;
  private final PageDispatcher pageDispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public PageManagerController(PageManagerDisplayFactory pageManagerDisplayFactory, PageDispatcher pageDispatcher,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {
    this.pageManagerDisplayFactory = pageManagerDisplayFactory;
    this.pageDispatcher = pageDispatcher;
    this.notificationCenter = notificationCenter;
    this.messageSource = messageSource;
  }

  /**
   * Mapping pour la page d'affichage de toute les NewsEntry
   * 
   * @return
   */
  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewPages(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_VIEW.name());
    return pageManagerDisplayFactory.computeModelAndViewForViewAllPages(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:pages:create')")
  public ModelAndView printCreatePage(Locale locale) {
    LOGGER.info("Accès à la page de création des pages");
    return pageManagerDisplayFactory.computeModelAndViewForCreatePage(locale);
  }

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:pages:create')")
  public ResponseEntity<PageResponse> createPage(@RequestBody PageCreateForm createForm, Locale locale) {

    LOGGER.info("Tentative de création d'une page");
    try {
      PageResponse response = pageDispatcher.createEntity(createForm, locale);
      if (response.getPage() != null) {
        LOGGER.info("Entrée crée, id " + response.getPage().getId());
      }
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));
      } else {
        notificationCenter.sendNotification(response.getError());
      }
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PutMapping(value = "/{pageId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:pages:write')")
  public ResponseEntity<PageResponse> updatePage(@RequestBody PageUpdateForm updateForm, Locale locale) {

    LOGGER.info("Tentative de modification d'une page");
    try {
      PageResponse response = pageDispatcher.updateEntity(updateForm, locale);
      if (response.getPage() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getPage().getId());
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

  @GetMapping(value = "/{pageId}")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePage(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId);
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePage(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_main")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageMain(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie main");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageMain(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_body")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageBody(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie body");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageBody(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_header")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageHeader(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie header");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageHeader(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_footer")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageFooter(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie footer");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageFooter(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_meta")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageMeta(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie meta");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageMeta(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_widgets")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageWidgets(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie widgets");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageWidgets(locale, pageId, languageCode);
  }

  @GetMapping(value = "/{pageId}/_amp")
  @PreAuthorize("hasAuthority('webmastering:pages:read')")
  public ModelAndView printViewUpdatePageAMP(@PathVariable(value = "pageId") String pageId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.PAGES_UPDATE.name() + " pour " + pageId + " pour la partie amp");
    return pageManagerDisplayFactory.computeModelAndViewForUpdatePageMeta(locale, pageId, languageCode);
  }

  @DeleteMapping(value = "/{pageId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:pages:delete')")
  public ResponseEntity<PageResponse> deletePage(@PathVariable(value = "pageId") String pageId, Locale locale) {

    LOGGER.info("Tentative de suppression d'une page");
    try {
      PageResponse response = pageDispatcher.deleteEntity(pageId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Page " + pageId + " supprimée");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression de la page " + pageId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
