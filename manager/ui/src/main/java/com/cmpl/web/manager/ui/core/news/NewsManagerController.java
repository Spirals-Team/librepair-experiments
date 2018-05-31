package com.cmpl.web.manager.ui.core.news;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.news.NewsEntryDispatcher;
import com.cmpl.web.core.news.NewsEntryRequest;
import com.cmpl.web.core.news.NewsEntryResponse;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

/**
 * Controller pour la gestion des NewsEntry dans le back office
 * 
 * @author Louis
 *
 */
@ManagerController
public class NewsManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsManagerController.class);

  private final NewsManagerDisplayFactory newsManagerDisplayFactory;
  private final NewsEntryDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public NewsManagerController(NewsManagerDisplayFactory newsManagerDisplayFactory, NewsEntryDispatcher dispatcher,
      NotificationCenter notificationCenter, WebMessageSource webMessageSource) {
    this.newsManagerDisplayFactory = newsManagerDisplayFactory;
    this.dispatcher = dispatcher;
    this.notificationCenter = notificationCenter;
    this.messageSource = webMessageSource;
  }

  /**
   * Mapping pour la page d'affichage de toute les NewsEntry
   * 
   * @return
   */
  @GetMapping(value = "/manager/news")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView printViewNews(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.NEWS_VIEW.name());
    return newsManagerDisplayFactory.computeModelAndViewForBackPage(locale, pageNumberToUse);
  }

  /**
   * Mapping pour la creation d'une NewsEntry
   * 
   * @return
   */
  @GetMapping(value = "/manager/news/_create")
  @PreAuthorize("hasAuthority('webmastering:news:create')")
  public ModelAndView printCreateNews(Locale locale) {

    LOGGER.info("Accès à la page " + BACK_PAGE.NEWS_CREATE.name());
    return newsManagerDisplayFactory.computeModelAndViewForBackPageCreateNews(locale);
  }

  /**
   * Mapping pour la creation d'une NewsEntry
   * 
   * @param newsEntryRequest
   * @return
   */
  @PostMapping(value = "/manager/news", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:news:create')")
  public ResponseEntity<NewsEntryResponse> createNewsEntry(@RequestBody NewsEntryRequest newsEntryRequest,
      Locale locale) {

    LOGGER.info("Tentative de création d'une entrée de blog");
    try {
      NewsEntryResponse response = dispatcher.createEntity(newsEntryRequest, locale);
      if (response.getNewsEntry() != null) {
        LOGGER.info("Entrée crée, id " + response.getNewsEntry().getId());
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

  /**
   * Mapping pour la modification d'une NewsEntry
   * 
   * @param newsEntryId
   * @param newsEntryRequest
   * @return
   */
  @PutMapping(value = "/manager/news/{newsEntryId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:news:write')")
  public ResponseEntity<NewsEntryResponse> updateNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      @RequestBody NewsEntryRequest newsEntryRequest, Locale locale) {

    LOGGER.info("Tentative de mise à jour d'une entrée de blog d'id " + newsEntryId);
    try {
      NewsEntryResponse response = dispatcher.updateEntity(newsEntryRequest, newsEntryId, locale);
      if (response.getError() == null) {
        notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
        LOGGER.info("Entrée modifiée, id " + newsEntryId);
      } else {
        notificationCenter.sendNotification(response.getError());
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la mise à jour de l'entrée d'id " + newsEntryId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  /**
   * Mapping pour la suppression d'une NewsEntry
   * 
   * @param newsEntryId
   * @return
   */
  @DeleteMapping(value = "/manager/news/{newsEntryId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:news:delete')")
  public ResponseEntity<NewsEntryResponse> deleteNewsEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      Locale locale) {
    LOGGER.info("Tentative de suppression d'une entrée d'id " + newsEntryId);
    NewsEntryResponse response = dispatcher.deleteEntity(newsEntryId, locale);
    notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
    LOGGER.info("NewsEntry " + newsEntryId + " supprimée");
    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
  }

  /**
   * Mapping pour la recuperation d'une NewsEntry
   * 
   * @param newsEntryId
   * @return
   */
  @GetMapping(value = "/manager/news/{newsEntryId}")
  @PreAuthorize("hasAuthority('webmastering:news:read')")
  public ModelAndView getNewsEntity(@PathVariable(value = "newsEntryId") String newsEntryId, Locale locale) {

    LOGGER.info("Récupération de l'entrée d'id " + newsEntryId);
    return newsManagerDisplayFactory.computeModelAndViewForOneNewsEntry(locale, newsEntryId);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @PostMapping(value = "/manager/news/media/{newsEntryId}", consumes = "multipart/form-data")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('webmastering:media:create')")
  public void uploadNewsImage(@RequestParam("media") MultipartFile uploadedMedia,
      @PathVariable(value = "newsEntryId") String newsEntryId, Locale locale) {
    if (uploadedMedia.isEmpty()) {
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return;
    }
    try {
      dispatcher.saveNewsMedia(newsEntryId, uploadedMedia);
      notificationCenter.sendNotification("success", messageSource.getMessage("create.error", locale));
    } catch (Exception e) {
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      LOGGER.error("Cannot save multipart file !", e);
    }
  }

}
