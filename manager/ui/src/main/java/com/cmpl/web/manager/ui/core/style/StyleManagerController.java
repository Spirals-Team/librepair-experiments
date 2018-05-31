package com.cmpl.web.manager.ui.core.style;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.style.StyleForm;
import com.cmpl.web.core.style.StyleResponse;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

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
    this.displayFactory = displayFactory;
    this.dispatcher = dispatcher;
    this.notificationCenter = notificationCenter;
    this.messageSource = messageSource;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printViewStyle(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());
    return displayFactory.computeModelAndViewForViewStyles(locale);
  }

  @GetMapping(value = "/_edit")
  @PreAuthorize("hasAuthority('webmastering:style:read')")
  public ModelAndView printEditStyle(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.STYLES_VIEW.name());
    return displayFactory.computeModelAndViewForUpdateStyles(locale);
  }

  @PutMapping(value = "/_edit", produces = "application/json")
  @PreAuthorize("hasAuthority('webmastering:style:write')")
  public ResponseEntity<StyleResponse> handleEditStyle(@RequestBody StyleForm form, Locale locale) {
    LOGGER.info("Tentative de modification du style global");
    try {
      StyleResponse response = dispatcher.updateEntity(form, locale);
      if (response.getStyle() != null) {
        LOGGER.info("Style global modifié");
      }
      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification du style global", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
