package com.cmpl.web.manager.ui.core.webmastering.carousel;

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

import com.cmpl.web.core.carousel.CarouselCreateForm;
import com.cmpl.web.core.carousel.CarouselDispatcher;
import com.cmpl.web.core.carousel.CarouselResponse;
import com.cmpl.web.core.carousel.CarouselUpdateForm;
import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager/carousels")
public class CarouselManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CarouselManagerController.class);

  private final CarouselDispatcher carouselDispatcher;
  private final CarouselManagerDisplayFactory carouselDisplayFactory;
  private final NotificationCenter notificationCenter;
  private final WebMessageSource messageSource;

  public CarouselManagerController(CarouselDispatcher carouselDispatcher,
      CarouselManagerDisplayFactory carouselDisplayFactory, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {
    this.carouselDisplayFactory = Objects.requireNonNull(carouselDisplayFactory);
    this.carouselDispatcher = Objects.requireNonNull(carouselDispatcher);
    this.notificationCenter = Objects.requireNonNull(notificationCenter);
    this.messageSource = Objects.requireNonNull(messageSource);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('webmastering:carousels:read')")
  public ModelAndView printViewCarousels(@RequestParam(name = "p", required = false) Integer pageNumber,
      Locale locale) {

    int pageNumberToUse = computePageNumberFromRequest(pageNumber);
    LOGGER.info("Accès à la page " + BACK_PAGE.CAROUSELS_VIEW.name());
    return carouselDisplayFactory.computeModelAndViewForViewAllCarousels(locale, pageNumberToUse);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

  @GetMapping(value = "/_create")
  @PreAuthorize("hasAuthority('webmastering:carousels:create')")
  public ModelAndView printCreateCarousel(Locale locale) {
    LOGGER.info("Accès à la page de création des carousels");
    return carouselDisplayFactory.computeModelAndViewForCreateCarousel(locale);
  }

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:carousels:create')")
  public ResponseEntity<CarouselResponse> createCarousel(@Valid @RequestBody CarouselCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'un carousel");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      CarouselResponse response = carouselDispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getCarousel().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @PutMapping(value = "/{carouselId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:carousels:write')")
  public ResponseEntity<CarouselResponse> updateCarousel(@Valid @RequestBody CarouselUpdateForm updateForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de modification d'un carousel");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("update.error", bindingResult, locale);
      LOGGER.error("Echec de la modification de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      CarouselResponse response = carouselDispatcher.updateEntity(updateForm, locale);

      LOGGER.info("Entrée modifiée, id " + response.getCarousel().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("update.success", locale));

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Echec de la modification de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("update.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @GetMapping(value = "/{carouselId}")
  @PreAuthorize("hasAuthority('webmastering:carousels:read')")
  public ModelAndView printViewUpdateCarousel(@PathVariable(value = "carouselId") String carouselId, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.CAROUSELS_VIEW.name() + " pour " + carouselId);
    return carouselDisplayFactory.computeModelAndViewForUpdateCarousel(locale, carouselId);
  }

  @GetMapping(value = "/{carouselId}/_main")
  @PreAuthorize("hasAuthority('webmastering:carousels:read')")
  public ModelAndView printViewUpdateCarouselMain(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER
        .info("Accès à la page " + BACK_PAGE.CAROUSELS_UPDATE.name() + " pour " + carouselId + " pour la partie main");
    return carouselDisplayFactory.computeModelAndViewForUpdateCarouselMain(carouselId);
  }

  @GetMapping(value = "/{carouselId}/_items")
  @PreAuthorize("hasAuthority('webmastering:carousels:read')")
  public ModelAndView printViewUpdateCarouselItems(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER
        .info("Accès à la page " + BACK_PAGE.CAROUSELS_UPDATE.name() + " pour " + carouselId + " pour la partie items");
    return carouselDisplayFactory.computeModelAndViewForUpdateCarouselItems(carouselId);
  }

  @PostMapping(value = "/{carouselId}/items")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:carousels:write')")
  public ResponseEntity<CarouselItemResponse> createCarouselItem(@Valid @RequestBody CarouselItemCreateForm createForm,
      BindingResult bindingResult, Locale locale) {

    LOGGER.info("Tentative de création d'un élément de carousel");
    if (bindingResult.hasErrors()) {
      notificationCenter.sendNotification("create.error", bindingResult, locale);
      LOGGER.error("Echec de la creation de l'entrée");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    try {
      CarouselItemResponse response = carouselDispatcher.createEntity(createForm, locale);

      LOGGER.info("Entrée crée, id " + response.getItem().getId());

      notificationCenter.sendNotification("success", messageSource.getMessage("create.success", locale));

      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      LOGGER.error("Echec de la creation de l'entrée", e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("create.error", locale));
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

  }

  @DeleteMapping(value = "/{carouselId}/items/{carouselItemId}")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:carousels:delete')")
  public ResponseEntity<CarouselItemResponse> deleteCarouselItem(@PathVariable(value = "carouselId") String carouselId,
      @PathVariable(value = "carouselItemId") String carouselItemId, Locale locale) {

    LOGGER.info("Tentative de suppression d'un élément de carousel");
    try {
      carouselDispatcher.deleteCarouselItemEntity(carouselId, carouselItemId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Element de carousel " + carouselItemId + " supprimé");
    } catch (Exception e) {
      LOGGER.error("Echec de la suppression de l'élément de carousel " + carouselItemId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{carouselId}", produces = "application/json")
  @ResponseBody
  @PreAuthorize("hasAuthority('webmastering:carousels:delete')")
  public ResponseEntity<CarouselResponse> deleteCarousel(@PathVariable(value = "carouselId") String carouselId,
      Locale locale) {

    LOGGER.info("Tentative de suppression d'un Carousel");

    try {
      CarouselResponse response = carouselDispatcher.deleteEntity(carouselId, locale);
      notificationCenter.sendNotification("success", messageSource.getMessage("delete.success", locale));
      LOGGER.info("Carousel " + carouselId + " supprimée");
      return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la suppression du Carousel " + carouselId, e);
      notificationCenter.sendNotification("danger", messageSource.getMessage("delete.error", locale));
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping(value = "/{carouselId}/_memberships")
  @PreAuthorize("hasAuthority('webmastering:carousels:read')")
  public ModelAndView printViewUpdateCarouselMemberships(@PathVariable(value = "carouselId") String carouselId) {
    LOGGER.info(
        "Accès à la page " + BACK_PAGE.CAROUSELS_UPDATE.name() + " pour " + carouselId + " pour la partie memberships");
    return carouselDisplayFactory.computeModelAndViewForMembership(carouselId);
  }
}
