package com.cmpl.web.core.carousel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarouselTranslatorImplTest {

  @Spy
  @InjectMocks
  private CarouselTranslatorImpl translator;

  @Test
  public void testFromCreateFormToDTOCarouselCreateForm() throws Exception {

    CarouselCreateForm form = CarouselCreateFormBuilder.create().name("someName").build();
    CarouselDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getName(), result.getName());

  }

  @Test
  public void testFromDTOToResponseCarouselDTO() throws Exception {
    CarouselDTO dto = CarouselDTOBuilder.create().build();
    CarouselResponse result = translator.fromDTOToResponse(dto);
    Assert.assertEquals(dto, result.getCarousel());
  }

  @Test
  public void testFromCreateFormToDTOCarouselItemCreateForm() throws Exception {
    CarouselItemCreateForm form = CarouselItemCreateFormBuilder.create().carouselId("123456789").build();
    CarouselItemDTO result = translator.fromCreateFormToDTO(form);

    Assert.assertEquals(form.getCarouselId(), result.getCarouselId());
  }

  @Test
  public void testFromDTOToResponseCarouselItemDTO() throws Exception {
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().build();
    CarouselItemResponse result = translator.fromDTOToResponse(dto);

    Assert.assertEquals(dto, result.getItem());
  }

}
