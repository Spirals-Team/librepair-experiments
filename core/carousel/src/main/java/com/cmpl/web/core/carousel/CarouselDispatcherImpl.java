package com.cmpl.web.core.carousel;

import java.util.Locale;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

public class CarouselDispatcherImpl implements CarouselDispatcher {

  private final CarouselService carouselService;
  private final CarouselItemService carouselItemService;
  private final MediaService mediaService;
  private final CarouselTranslator translator;
  private final CarouselValidator validator;

  public CarouselDispatcherImpl(CarouselService carouselService, CarouselItemService carouselItemService,
      MediaService mediaService, CarouselTranslator carouselTransaltor, CarouselValidator carouselValidator) {
    this.carouselItemService = carouselItemService;
    this.carouselService = carouselService;
    this.translator = carouselTransaltor;
    this.mediaService = mediaService;
    this.validator = carouselValidator;
  }

  @Override
  public CarouselResponse createEntity(CarouselCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return CarouselResponseBuilder.create().error(error).build();

    }

    CarouselDTO carouselToCreate = translator.fromCreateFormToDTO(form);
    CarouselDTO createdCarousel = carouselService.createEntity(carouselToCreate);
    return translator.fromDTOToResponse(createdCarousel);
  }

  @Override
  public CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale) {
    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return CarouselResponseBuilder.create().error(error).build();

    }

    CarouselDTO carouselToUpdate = carouselService.getEntity(form.getId());
    carouselToUpdate.setName(form.getName());

    CarouselDTO updatedCarousel = carouselService.updateEntity(carouselToUpdate);

    return translator.fromDTOToResponse(updatedCarousel);
  }

  @Override
  public CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale) {
    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return CarouselItemResponseBuilder.create().error(error).build();
    }

    CarouselItemDTO itemToCreate = translator.fromCreateFormToDTO(form);
    MediaDTO media = mediaService.getEntity(Long.valueOf(form.getMediaId()));
    itemToCreate.setMedia(media);
    CarouselItemDTO createdItem = carouselItemService.createEntity(itemToCreate);

    return translator.fromDTOToResponse(createdItem);
  }

  @Override
  public CarouselResponse deleteEntity(String carouselId, Locale locale) {
    carouselService.deleteEntity(Long.parseLong(carouselId));
    return CarouselResponseBuilder.create().build();
  }

  @Override
  public void deleteCarouselItemEntity(String carouselId, String carouselItemId, Locale locale) throws BaseException {
    Error error = validator.validateDelete(carouselItemId, locale);
    if (error != null) {
      throw new BaseException(error.getCauses().get(0).getMessage());
    }
    carouselItemService.deleteEntityInCarousel(carouselId, Long.valueOf(carouselItemId));
  }

}
