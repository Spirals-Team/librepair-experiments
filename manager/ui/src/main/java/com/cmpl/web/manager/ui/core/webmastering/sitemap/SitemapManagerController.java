package com.cmpl.web.manager.ui.core.webmastering.sitemap;

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
import com.cmpl.web.core.sitemap.SitemapCreateForm;
import com.cmpl.web.core.sitemap.SitemapDispatcher;
import com.cmpl.web.core.sitemap.SitemapResponse;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
public class SitemapManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SitemapManagerController.class);

  private final SitemapDispatcher dispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public SitemapManagerController(SitemapDispatcher dispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {

    this.dispatcher = Objects.requireNonNull(dispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @PostMapping(value = "/manager/sitemaps", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:sitemaps:create')")
  public ResponseEntity<SitemapResponse> createSitemap(@Valid @RequestBody SitemapCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'une association website/page");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      SitemapResponse response = dispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getSitemapDTO().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/manager/sitemaps/{websiteId}/{pageId}", produces = "application/json")
  @PreAuthorize("hasAuthority('webmastering:sitemaps:delete')")
  public ResponseEntity<SitemapResponse> deleteSitemap(@PathVariable(name = "websiteId") String websiteId,
      @PathVariable(name = "pageId") String pageId, Locale locale) {
    LOGGER.info("Tentative de suppression d'une association website page");

    try {
      dispatcher.deleteEntity(Long.parseLong(websiteId), Long.parseLong(pageId));
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Association entre le website " + websiteId + " et la page " + pageId + " supprimée");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'association website/page pour  l'association entre le website d'id "
          + websiteId + " et la page d'id " + pageId, e);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
