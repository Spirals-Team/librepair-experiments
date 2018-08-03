package com.cmpl.web.core.carousel;

import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;

public interface CarouselTranslator {

  CarouselDTO fromCreateFormToDTO(CarouselCreateForm form);

  CarouselItemDTO fromCreateFormToDTO(CarouselItemCreateForm form);

  CarouselResponse fromDTOToResponse(CarouselDTO dto);

  CarouselItemResponse fromDTOToResponse(CarouselItemDTO dto);
}
