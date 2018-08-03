package com.cmpl.web.core.news.content;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.models.NewsContent;

@RunWith(MockitoJUnitRunner.class)
public class NewsContentServiceImplTest {

  @Mock
  private NewsContentMapper mapper;

  @Mock
  private NewsContentDAO newsContentDAO;

  @Mock
  private ApplicationEventPublisher publisher;

  @InjectMocks
  @Spy
  private NewsContentServiceImpl service;

  @Test
  public void testDeleteEntity() {

    BDDMockito.doNothing().when(newsContentDAO).deleteEntity(BDDMockito.anyLong());

    service.deleteEntity(1L);

    BDDMockito.verify(newsContentDAO, BDDMockito.times(1)).deleteEntity(BDDMockito.eq(1L));

  }

  @Test
  public void testGetEntities_No_Result() {

    BDDMockito.doReturn(Arrays.asList()).when(newsContentDAO).getEntities();

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testGetEntities_With_Results() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    NewsContent content2 = new NewsContent();
    content2.setContent("content2");

    LocalDateTime date = LocalDateTime.now();

    List<NewsContent> contents = Arrays.asList(content1, content2);

    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();
    NewsContentDTO contentDTO2 = NewsContentDTOBuilder.create().content("content2").id(1L).creationDate(date)
        .modificationDate(date).build();

    List<NewsContentDTO> contentsDTO = Arrays.asList(contentDTO1, contentDTO2);

    BDDMockito.doReturn(contents).when(newsContentDAO).getEntities();
    BDDMockito.doReturn(contentsDTO).when(mapper).toListDTO(BDDMockito.eq(contents));

    List<NewsContentDTO> result = service.getEntities();

    Assert.assertEquals(content1.getContent(), result.get(0).getContent());
    Assert.assertEquals(content2.getContent(), result.get(1).getContent());

  }

  @Test
  public void testUpdateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");

    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.given(mapper.toDTO(BDDMockito.any(NewsContent.class))).willReturn(contentDTO1);
    BDDMockito.given(mapper.toEntity(BDDMockito.any(NewsContentDTO.class))).willReturn(content1);
    BDDMockito.given(newsContentDAO.updateEntity(BDDMockito.any(NewsContent.class))).willReturn(content1);

    NewsContentDTO result = service.updateEntity(contentDTO1);

    Assert.assertEquals(contentDTO1, result);

  }

  @Test
  public void testGetEntity_Null() {

    BDDMockito.doReturn(null).when(newsContentDAO).getEntity(BDDMockito.anyLong());

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertNull(result);
  }

  @Test
  public void testGetEntity_Not_Null() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    NewsContent optional = content1;

    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.doReturn(optional).when(newsContentDAO).getEntity(BDDMockito.anyLong());
    BDDMockito.doReturn(contentDTO1).when(mapper).toDTO(BDDMockito.eq(content1));

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertEquals(content1.getContent(), result.getContent());
  }

  @Test
  public void testCreateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.given(mapper.toEntity(BDDMockito.any(NewsContentDTO.class))).willReturn(content1);
    BDDMockito.given(mapper.toDTO(BDDMockito.any(NewsContent.class))).willReturn(contentDTO1);
    BDDMockito.given(newsContentDAO.createEntity(BDDMockito.any(NewsContent.class))).willReturn(content1);

    NewsContentDTO result = service.createEntity(contentDTO1);

    Assert.assertEquals(contentDTO1, result);

  }
}
