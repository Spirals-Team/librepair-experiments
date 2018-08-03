package com.cmpl.web.core.media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.Media;

@RunWith(MockitoJUnitRunner.class)
public class MediaMapperTest {

  @Spy
  private MediaMapper mapper;

  @Test
  public void testToEntity() throws Exception {
    MediaDTO dto = MediaDTOBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(MediaDTO.class), BDDMockito.any(Media.class));
    mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(MediaDTO.class),
        BDDMockito.any(Media.class));
  }

  @Test
  public void testToDTO() throws Exception {
    Media entity = MediaBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Media.class), BDDMockito.any(MediaDTO.class));
    mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(Media.class),
        BDDMockito.any(MediaDTO.class));
  }

}
