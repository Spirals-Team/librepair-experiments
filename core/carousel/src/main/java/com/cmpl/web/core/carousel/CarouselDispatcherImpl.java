package com.cmpl.web.core.carousel;

import java.util.Locale;
import java.util.Objects;

import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;

public class CarouselDispatcherImpl implements CarouselDispatcher {

  private final CarouselService carouselService;
  private final CarouselItemService carouselItemService;
  private final MediaService mediaService;
  private final CarouselTranslator translator;

  public CarouselDispatcherImpl(CarouselService carouselService, CarouselItemService carouselItemService,
      MediaService mediaService, CarouselTranslator carouselTransaltor) {
    this.carouselItemService = Objects.requireNonNull(carouselItemService);
    this.carouselService = Objects.requireNonNull(carouselService);
    this.translator = Objects.requireNonNull(carouselTransaltor);
    this.mediaService = Objects.requireNonNull(mediaService);

  }

  @Override
  public CarouselResponse createEntity(CarouselCreateForm form, Locale locale) {

    CarouselDTO carouselToCreate = translator.fromCreateFormToDTO(form);
    CarouselDTO createdCarousel = carouselService.createEntity(carouselToCreate);
    return translator.fromDTOToResponse(createdCarousel);
  }

  @Override
  public CarouselResponse updateEntity(CarouselUpdateForm form, Locale locale) {

    CarouselDTO carouselToUpdate = carouselService.getEntity(form.getId());
    carouselToUpdate.setName(form.getName());

    CarouselDTO updatedCarousel = carouselService.updateEntity(carouselToUpdate);

    return translator.fromDTOToResponse(updatedCarousel);
  }

  @Override
  public CarouselItemResponse createEntity(CarouselItemCreateForm form, Locale locale) {

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

    carouselItemService.deleteEntityInCarousel(carouselId, Long.valueOf(carouselItemId));
  }

}
