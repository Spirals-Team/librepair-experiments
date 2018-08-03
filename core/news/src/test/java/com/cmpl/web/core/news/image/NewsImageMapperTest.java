package com.cmpl.web.core.news.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.NewsImage;

@RunWith(MockitoJUnitRunner.class)
public class NewsImageMapperTest {

  @Mock
  private MediaService mediaService;

  @Spy
  @InjectMocks
  private NewsImageMapper mapper;

  @Test
  public void testToEntity() throws Exception {

    NewsImageDTO dto = new NewsImageDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsImage.class));
    NewsImage result = mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsImage entity = new NewsImage();
    entity.setId(1L);

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.eq(entity), BDDMockito.any(NewsImageDTO.class));
    NewsImageDTO result = mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

}
