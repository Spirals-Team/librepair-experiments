package com.cmpl.web.core.news.content;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.NewsContent;

@RunWith(MockitoJUnitRunner.class)
public class NewsContentMapperTest {

  @Spy
  private NewsContentMapper mapper;

  @Test
  public void testToEntity() throws Exception {

    NewsContentDTO dto = new NewsContentDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsContent.class));
    NewsContent result = mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsContent entity = new NewsContent();
    entity.setId(1L);

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.eq(entity), BDDMockito.any(NewsContentDTO.class));
    NewsContentDTO result = mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

  @Test
  public void testFillObject() throws Exception {
    LocalDateTime date = LocalDateTime.now();
    NewsContentDTO dto = NewsContentDTOBuilder.create().content("someContent").id(1L).creationDate(date)
        .modificationDate(date).build();

    NewsContent destination = new NewsContent();

    mapper.fillObject(dto, destination);

    Assert.assertEquals(dto.getId(), destination.getId());
    Assert.assertEquals(dto.getCreationDate(), destination.getCreationDate());
    Assert.assertEquals(dto.getModificationDate(), destination.getModificationDate());
    Assert.assertEquals(dto.getContent(), destination.getContent());

  }

  @Test
  public void testToListDTO() throws Exception {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    NewsContent content2 = new NewsContent();
    content2.setContent("content2");

    LocalDateTime date = LocalDateTime.now();

    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();
    NewsContentDTO contentDTO2 = NewsContentDTOBuilder.create().content("content2").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.doReturn(contentDTO1).when(mapper).toDTO(BDDMockito.eq(content1));
    BDDMockito.doReturn(contentDTO2).when(mapper).toDTO(BDDMockito.eq(content2));

    List<NewsContentDTO> result = mapper.toListDTO(Arrays.asList(content1, content2));

    Assert.assertEquals(contentDTO1, result.get(0));
    Assert.assertEquals(contentDTO2, result.get(1));

  }

}
