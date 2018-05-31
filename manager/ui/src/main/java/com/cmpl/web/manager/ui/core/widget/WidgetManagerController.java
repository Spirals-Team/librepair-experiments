package com.cmpl.web.manager.ui.core.widget;

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
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.widget.WidgetCreateForm;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.core.widget.WidgetResponse;
import com.cmpl.web.core.widget.WidgetUpdateForm;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/widgets")
public class WidgetManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetManagerController.class);

  private final WidgetManagerDisplayFactory widgetManagerDisplayFactory;
  private final WidgetDispatcher widgetDispatcher;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public WidgetManagerController(WidgetManagerDisplayFactory widgetManagerDisplayFactory,
      WidgetDispatcher widgetDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    this.widgetManagerDisplayFactory = widgetManagerDisplayFactory;
    this.widgetDispatcher = widgetDispatcher;
    this.notificationCenter = notificationCenter;
    this.messageSource = messageSource;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewWidgets(@RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.WIDGET_VIEW.name());
    return widgetManagerDisplayFactory.computeModelAndViewForViewAllWidgets(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ModelAndView printCreateWidget(Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WIDGET_CREATE.name());
    return widgetManagerDisplayFactory.computeModelAndViewForCreateWidget(locale);
  }

  @PostMapping(produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:create')")
  public ResponseEntity<WidgetResponse> createWidget(@RequestBody WidgetCreateForm createForm, Locale locale) {
    LOGGER.info("Tentative de création d'une page");
    try {
      WidgetResponse response = widgetDispatcher.createEntity(createForm, locale);
      if (response.getWidget() != null) {
        LOGGER.info("Entrée crée, id " + response.getWidget().getId());
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

  @GetMapping(value = "/{widgetId}")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidget(@PathVariable(value = "widgetId") String widgetId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WIDGET_UPDATE.name() + " pour " + widgetId);
    return widgetManagerDisplayFactory.computeModelAndViewForUpdateWidget(locale, widgetId, languageCode);
  }

  @GetMapping(value = "/{widgetId}/_main")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidgetMain(@PathVariable(value = "widgetId") String widgetId, Locale locale,
      @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info("Accès à la page " + BACK_PAGE.WIDGET_UPDATE.name() + " pour " + widgetId + " pour la partie main");
    return widgetManagerDisplayFactory.computeModelAndViewForUpdateWidgetMain(locale, widgetId, languageCode);
  }

  @GetMapping(value = "/{widgetId}/_personalization")
  @PreAuthorize("hasAuthority('webmastering:widgets:read')")
  public ModelAndView printViewUpdateWidgetPersonalization(@PathVariable(value = "widgetId") String widgetId,
      Locale locale, @RequestParam(name = "languageCode", required = false) String languageCode) {
    LOGGER.info(
        "Accès à la page " + BACK_PAGE.WIDGET_UPDATE.name() + " pour " + widgetId + " pour la partie personnalisation");
    return widgetManagerDisplayFactory.computeModelAndViewForUpdateWidgetPersonalization(locale, widgetId,
        languageCode);
  }

  @PutMapping(value = "/{widgetId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:write')")
  public ResponseEntity<WidgetResponse> updateWidget(@RequestBody WidgetUpdateForm updateForm, Locale locale) {
    LOGGER.info("Tentative de création d'une page");
    try {
      WidgetResponse response = widgetDispatcher.updateEntity(updateForm, locale);
      if (response.getWidget() != null) {
        LOGGER.info("Entrée modifiée, id " + response.getWidget().getId());
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

  @DeleteMapping(value = "/{widgetId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:widgets:delete')")
  public ResponseEntity<WidgetResponse> deleteWidget(@PathVariable(value = "widgetId") String widgetId, Locale locale) {
    LOGGER.info("Tentative de création d'une page");
    try {
      WidgetResponse response = widgetDispatcher.deleteEntity(widgetId, locale);

      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
