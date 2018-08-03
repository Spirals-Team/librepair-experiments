package com.cmpl.web.core.news.entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageService;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryMapperTest {

  @Mock
  private NewsContentService newsContentService;
  @Mock
  private NewsImageService newsImageService;

  @Spy
  @InjectMocks
  private NewsEntryMapper mapper;

  @Test
  public void testToEntity() throws Exception {

    NewsEntryDTO dto = NewsEntryDTOBuilder.create().build();
    dto.setId(1L);

    NewsEntry result = mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsEntry entity = NewsEntryBuilder.create().build();
    entity.setId(1L);

    NewsEntryDTO result = mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }
}
