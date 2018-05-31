package com.cmpl.web.core.carousel;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CarouselServiceImplTest {

  @Mock
  private CarouselRepository carouselRepository;
  @Mock
  private CarouselItemService carouselItemService;

  @Spy
  @InjectMocks
  private CarouselServiceImpl carouselService;

  @Test
  public void testToEntity() throws Exception {
    CarouselDTO dto = CarouselDTOBuilder.create().build();

    BDDMockito.doNothing().when(carouselService)
        .fillObject(BDDMockito.any(CarouselDTO.class), BDDMockito.any(Carousel.class));
    carouselService.toEntity(dto);

    BDDMockito.verify(carouselService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselDTO.class),
        BDDMockito.any(Carousel.class));
  }

  @Test
  public void testToDTO() throws Exception {

    Carousel entity = CarouselBuilder.create().id(123456789l).build();
    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    BDDMockito.given(carouselItemService.getByCarouselId(BDDMockito.anyString())).willReturn(Arrays.asList(dto));

    BDDMockito.doNothing().when(carouselService)
        .fillObject(BDDMockito.any(Carousel.class), BDDMockito.any(CarouselDTO.class));
    CarouselDTO result = carouselService.toDTO(entity);
    Assert.assertEquals(dto, result.getCarouselItems().get(0));

    BDDMockito.verify(carouselService, BDDMockito.times(1)).fillObject(BDDMockito.any(Carousel.class),
        BDDMockito.any(CarouselDTO.class));
  }

}
