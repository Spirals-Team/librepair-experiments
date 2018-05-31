package com.cmpl.web.core.carousel;

public class CarouselTranslatorImpl implements CarouselTranslator {

  @Override
  public CarouselDTO fromCreateFormToDTO(CarouselCreateForm form) {
    return CarouselDTOBuilder.create().name(form.getName()).build();
  }

  @Override
  public CarouselResponse fromDTOToResponse(CarouselDTO dto) {
    return CarouselResponseBuilder.create().carousel(dto).build();
  }

  @Override
  public CarouselItemDTO fromCreateFormToDTO(CarouselItemCreateForm form) {
    return CarouselItemDTOBuilder.create().carouselId(form.getCarouselId()).orderInCarousel(form.getOrderInCarousel())
        .build();
  }

  @Override
  public CarouselItemResponse fromDTOToResponse(CarouselItemDTO dto) {
    return CarouselItemResponseBuilder.create().item(dto).build();
  }

}
