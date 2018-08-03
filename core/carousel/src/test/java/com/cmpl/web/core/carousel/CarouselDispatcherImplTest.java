package com.cmpl.web.core.carousel;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.carousel.item.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.item.CarouselItemCreateFormBuilder;
import com.cmpl.web.core.carousel.item.CarouselItemDTO;
import com.cmpl.web.core.carousel.item.CarouselItemDTOBuilder;
import com.cmpl.web.core.carousel.item.CarouselItemResponse;
import com.cmpl.web.core.carousel.item.CarouselItemResponseBuilder;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private CarouselService carouselService;
  @Mock
  private CarouselItemService carouselItemService;
  @Mock
  private MediaService mediaService;
  @Mock
  private CarouselTranslator translator;

  @Spy
  @InjectMocks
  CarouselDispatcherImpl dispatcher;

  @Test
  public void testDeleteCarouselItemEntity_No_Error() throws Exception {

    BDDMockito.doNothing().when(carouselItemService).deleteEntityInCarousel(BDDMockito.anyString(),
        BDDMockito.anyLong());

    dispatcher.deleteCarouselItemEntity("123456789", "123456789", Locale.FRANCE);

    BDDMockito.verify(carouselItemService, BDDMockito.times(1)).deleteEntityInCarousel(BDDMockito.anyString(),
        BDDMockito.anyLong());
  }

  @Test
  public void testCreateEntityCarouselCreateFormLocale_No_Error() throws Exception {

    CarouselDTO dto = CarouselDTOBuilder.create().build();
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(CarouselCreateForm.class))).willReturn(dto);
    BDDMockito.given(carouselService.createEntity(BDDMockito.any(CarouselDTO.class))).willReturn(dto);
    CarouselResponse response = CarouselResponseBuilder.create().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselDTO.class))).willReturn(response);

    Assert.assertEquals(response, dispatcher.createEntity(CarouselCreateFormBuilder.create().build(), Locale.FRANCE));

  }

  @Test
  public void testUpdateEntity_No_Error() throws Exception {

    CarouselDTO dto = CarouselDTOBuilder.create().build();
    BDDMockito.given(carouselService.getEntity(BDDMockito.anyLong())).willReturn(dto);
    BDDMockito.given(carouselService.updateEntity(BDDMockito.any(CarouselDTO.class))).willReturn(dto);
    CarouselResponse response = CarouselResponseBuilder.create().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselDTO.class))).willReturn(response);

    Assert.assertEquals(response,
        dispatcher.updateEntity(CarouselUpdateFormBuilder.create().id(123456789l).build(), Locale.FRANCE));
  }

  @Test
  public void testCreateEntityCarouselItemCreateFormLocale_No_Error() throws Exception {

    CarouselItemDTO dto = CarouselItemDTOBuilder.create().build();
    BDDMockito.given(translator.fromCreateFormToDTO(BDDMockito.any(CarouselItemCreateForm.class))).willReturn(dto);
    BDDMockito.given(carouselItemService.createEntity(BDDMockito.any(CarouselItemDTO.class))).willReturn(dto);
    CarouselItemResponse response = CarouselItemResponseBuilder.create().build();
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(CarouselItemDTO.class))).willReturn(response);
    MediaDTO media = MediaDTOBuilder.create().build();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    Assert.assertEquals(response,
        dispatcher.createEntity(CarouselItemCreateFormBuilder.create().mediaId("123456789").build(), Locale.FRANCE));
  }

}
