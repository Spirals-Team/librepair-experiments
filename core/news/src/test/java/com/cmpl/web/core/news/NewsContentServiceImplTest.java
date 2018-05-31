package com.cmpl.web.core.news;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class NewsContentServiceImplTest {

  @Mock
  private NewsContentRepository repository;

  @Mock
  private ApplicationEventPublisher publisher;

  @InjectMocks
  @Spy
  private NewsContentServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsContentDTO dto = new NewsContentDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsContent.class));
    NewsContent result = service.toEntity(dto);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsContent entity = new NewsContent();
    entity.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(entity), BDDMockito.any(NewsContentDTO.class));
    NewsContentDTO result = service.toDTO(entity);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

  @Test
  public void testFillObject() throws Exception {
    LocalDateTime date = LocalDateTime.now();
    NewsContentDTO dto = NewsContentDTOBuilder.create().content("someContent").id(1L).creationDate(date)
        .modificationDate(date).build();

    NewsContent destination = new NewsContent();

    service.fillObject(dto, destination);

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

    BDDMockito.doReturn(contentDTO1).when(service).toDTO(BDDMockito.eq(content1));
    BDDMockito.doReturn(contentDTO2).when(service).toDTO(BDDMockito.eq(content2));

    List<NewsContentDTO> result = service.toListDTO(Arrays.asList(content1, content2));

    Assert.assertEquals(contentDTO1, result.get(0));
    Assert.assertEquals(contentDTO2, result.get(1));

  }

  @Test
  public void testDeleteEntity() {

    BDDMockito.doReturn(NewsContentDTOBuilder.create().build()).when(service).toDTO(BDDMockito.any(NewsContent.class));
    BDDMockito.given(repository.getOne(BDDMockito.anyLong())).willReturn(NewsContentBuilder.create().build());

    BDDMockito.doNothing().when(repository).deleteById(BDDMockito.anyLong());

    service.deleteEntity(1L);

    BDDMockito.verify(repository, BDDMockito.times(1)).deleteById(BDDMockito.eq(1L));

  }

  @Test
  public void testGetEntities_No_Result() {

    BDDMockito.doReturn(Arrays.asList()).when(repository).findAll(BDDMockito.any(Sort.class));

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

    BDDMockito.doReturn(contents).when(repository).findAll(BDDMockito.any(Sort.class));
    BDDMockito.doReturn(contentsDTO).when(service).toListDTO(BDDMockito.eq(contents));

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

    BDDMockito.doReturn(content1).when(service).toEntity(BDDMockito.eq(contentDTO1));
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(BDDMockito.eq(content1));
    BDDMockito.doReturn(content1).when(repository).save(BDDMockito.eq(content1));

    NewsContentDTO result = service.updateEntity(contentDTO1);

    Assert.assertTrue(date.isBefore(result.getModificationDate()));

  }

  @Test
  public void testGetEntity_Null() {

    BDDMockito.doReturn(null).when(repository).findById(BDDMockito.anyLong());

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertNull(result);
  }

  @Test
  public void testGetEntity_Not_Null() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    Optional<NewsContent> optional = Optional.of(content1);

    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.doReturn(optional).when(repository).findById(BDDMockito.anyLong());
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(BDDMockito.eq(content1));

    NewsContentDTO result = service.getEntity(1L);

    Assert.assertEquals(content1.getContent(), result.getContent());
  }

  @Test
  public void testcreateEntity() {

    NewsContent content1 = new NewsContent();
    content1.setContent("content1");
    LocalDateTime date = LocalDateTime.now();
    date = date.minusDays(1);
    NewsContentDTO contentDTO1 = NewsContentDTOBuilder.create().content("content1").id(1L).creationDate(date)
        .modificationDate(date).build();

    BDDMockito.doReturn(content1).when(service).toEntity(BDDMockito.eq(contentDTO1));
    BDDMockito.doReturn(contentDTO1).when(service).toDTO(BDDMockito.eq(content1));
    BDDMockito.doReturn(content1).when(repository).save(BDDMockito.eq(content1));

    NewsContentDTO result = service.createEntity(contentDTO1);

    Assert.assertTrue(date.isBefore(result.getModificationDate()));

  }
}
