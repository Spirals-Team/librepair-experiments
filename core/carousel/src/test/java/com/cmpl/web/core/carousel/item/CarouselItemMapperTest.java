package com.cmpl.web.core.carousel.item;

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
import com.cmpl.web.core.models.CarouselItem;

@RunWith(MockitoJUnitRunner.class)
public class CarouselItemMapperTest {

  @Mock
  private MediaService mediaService;

  @Spy
  @InjectMocks
  private CarouselItemMapper mapper;

  @Test
  public void testToEntity() throws Exception {
    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    CarouselItemDTO dto = CarouselItemDTOBuilder.create().media(media).build();

    mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
  }

  @Test
  public void testToDTO() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().build();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    CarouselItem entity = CarouselItemBuilder.create().mediaId("123456789").build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
    CarouselItemDTO result = mapper.toDTO(entity);
    Assert.assertEquals(media, result.getMedia());

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(CarouselItem.class),
        BDDMockito.any(CarouselItemDTO.class));
  }

}
