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
import com.cmpl.web.core.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselItemServiceImplTest {

  @Mock
  private CarouselItemRepository carouselItemRepository;
  @Mock
  private MediaService mediaService;

  @Spy
  @InjectMocks
  private CarouselItemServiceImpl carouselItemService;

  @Test
  public void testToEntity() throws Exception {
    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    carouselItemService.toEntity(dto);

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
  }

  @Test
  public void testToDTO() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().build();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    CarouselItem entity = CarouselItemBuilder.create().mediaId("123456789").build();

    BDDMockito.doNothing().when(carouselItemService)
        .fillObject(BDDMockito.any(CarouselItem.class), BDDMockito.any(CarouselItemDTO.class));
    CarouselItemDTO result = carouselItemService.toDTO(entity);
    Assert.assertEquals(media, result.getMedia());

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
  }

  @Test
  public void testCreateEntity() throws Exception {
    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    BDDMockito.doReturn(dto).when(carouselItemService).toDTO(BDDMockito.any(CarouselItem.class));

    CarouselItem entity = CarouselItemBuilder.create().build();
    BDDMockito.given(carouselItemRepository.save(BDDMockito.any(CarouselItem.class))).willReturn(entity);

    Assert.assertEquals(dto, carouselItemService.createEntity(dto));

  }

  @Test
  public void testGetByCarouselId() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    BDDMockito.doReturn(Arrays.asList(dto)).when(carouselItemService).toListDTO(BDDMockito.anyList());
    CarouselItem entity = CarouselItemBuilder.create().build();
    BDDMockito.given(carouselItemRepository.findByCarouselIdOrderByOrderInCarousel(BDDMockito.anyString())).willReturn(
        Arrays.asList(entity));

    Assert.assertEquals(dto, carouselItemService.getByCarouselId("123456789").get(0));

  }

}
