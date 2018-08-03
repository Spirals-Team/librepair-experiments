package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.carousel.item.CarouselItemDTOBuilder;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;
import com.cmpl.web.core.carousel.item.CarouselItemResponseBuilder;

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
