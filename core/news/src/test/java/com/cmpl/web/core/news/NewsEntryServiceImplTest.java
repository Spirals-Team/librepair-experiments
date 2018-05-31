package com.cmpl.web.core.news;

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
import org.springframework.util.CollectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryServiceImplTest {

  @Mock
  private NewsEntryRepository repository;

  @Mock
  private NewsContentService newsContentService;

  @Mock
  private NewsImageService newsImageService;

  @InjectMocks
  @Spy
  private NewsEntryServiceImpl service;

  @Test
  public void testToEntity() throws Exception {

    NewsEntryDTO dto = new NewsEntryDTO();
    dto.setId(1L);

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(dto), BDDMockito.any(NewsEntry.class));
    NewsEntry result = service.toEntity(dto);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(dto), BDDMockito.eq(result));
  }

  @Test
  public void testToDTO() throws Exception {

    NewsEntry entity = new NewsEntry();
    entity.setId(1L);

    NewsEntryDTO result = service.toDTO(entity);

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.eq(entity), BDDMockito.eq(result));
  }

  @Test
  public void testGetEntities_No_Result() throws Exception {

    BDDMockito.doReturn(Arrays.asList()).when(repository).findAll();

    List<NewsEntryDTO> result = service.getEntities();

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testGetEntities_With_Result() throws Exception {

    NewsEntry entry1 = new NewsEntry();
    entry1.setId(1L);

    NewsEntry entry2 = new NewsEntry();
    entry2.setId(1L);

    NewsEntryDTO dto1 = NewsEntryDTOBuilder.create().id(1L).build();

    NewsEntryDTO dto2 = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(Arrays.asList(entry1, entry2)).when(repository).findAll();
    BDDMockito.doReturn(dto1).when(service).toDTO(entry1);
    BDDMockito.doReturn(dto2).when(service).toDTO(entry2);

    List<NewsEntryDTO> result = service.getEntities();

    Assert.assertEquals(dto1, result.get(0));
    Assert.assertEquals(dto2, result.get(1));

  }

  @Test
  public void testGetEntity() throws Exception {

    NewsEntry entry = new NewsEntry();
    entry.setId(1L);
    Optional<NewsEntry> optional = Optional.of(entry);

    NewsEntryDTO dto = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(dto).when(service).toDTO(entry);
    BDDMockito.doReturn(optional).when(repository).findById(1L);

    NewsEntryDTO result = service.getEntity(1L);

    Assert.assertEquals(dto, result);
  }

  @Test
  public void testDealWithImageToUpdate_Create() throws Exception {

    NewsImageDTO formattingImage = NewsImageDTOBuilder.create().build();

    service.dealWithImageToUpdate(formattingImage);

    BDDMockito.verify(newsImageService, BDDMockito.times(1)).createEntity(BDDMockito.eq(formattingImage));
    BDDMockito.verify(newsImageService, BDDMockito.times(0)).updateEntity(BDDMockito.eq(formattingImage));

  }

  @Test
  public void testDealWithImageToUpdate_Update() throws Exception {

    NewsImageDTO formattingImage = NewsImageDTOBuilder.create().id(1L).build();
    NewsImageDTO formattedImage = NewsImageDTOBuilder.create().build();

    BDDMockito.doReturn(formattedImage).when(newsImageService).updateEntity(BDDMockito.eq(formattingImage));

    NewsImageDTO result = service.dealWithImageToUpdate(formattingImage);

    Assert.assertEquals(formattedImage, result);

    BDDMockito.verify(newsImageService, BDDMockito.times(0)).createEntity(BDDMockito.eq(formattingImage));
    BDDMockito.verify(newsImageService, BDDMockito.times(1)).updateEntity(BDDMockito.eq(formattingImage));
  }

  @Test
  public void testDealWithContentToUpdate_Create() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = NewsContentDTOBuilder.create().content(content).build();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).createEntity(BDDMockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    BDDMockito.verify(newsContentService, BDDMockito.times(1)).createEntity(BDDMockito.eq(contentToDealWith));
    BDDMockito.verify(newsContentService, BDDMockito.times(0)).updateEntity(BDDMockito.eq(contentToDealWith));

  }

  @Test
  public void testDealWithContentToUpdate_Update() throws Exception {
    String content = "content";
    NewsContentDTO contentToDealWith = NewsContentDTOBuilder.create().content(content).id(1L).build();

    BDDMockito.doReturn(contentToDealWith).when(newsContentService).updateEntity(BDDMockito.eq(contentToDealWith));

    NewsContentDTO result = service.dealWithContentToUpdate(contentToDealWith);

    Assert.assertEquals(contentToDealWith, result);

    BDDMockito.verify(newsContentService, BDDMockito.times(0)).createEntity(BDDMockito.eq(contentToDealWith));
    BDDMockito.verify(newsContentService, BDDMockito.times(1)).updateEntity(BDDMockito.eq(contentToDealWith));
  }

  @Test
  public void testUpdateImage_Null() throws Exception {
    NewsImageDTO result = service.updateImage(null);

    Assert.assertNull(result);

    BDDMockito.verify(service, BDDMockito.times(0)).dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateImage_Not_Null() throws Exception {

    NewsImageDTO formattingImage = NewsImageDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(formattingImage).when(service).dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class));

    NewsImageDTO result = service.updateImage(formattingImage);

    Assert.assertEquals(formattingImage, result);

    BDDMockito.verify(service, BDDMockito.times(1)).dealWithImageToUpdate(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testUpdateContent_Null() throws Exception {
    NewsContentDTO result = service.updateContent(null);

    Assert.assertNull(result);

    BDDMockito.verify(service, BDDMockito.times(0)).dealWithContentToUpdate(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateContent_Not_Null() throws Exception {

    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(content).when(service).dealWithContentToUpdate(BDDMockito.eq(content));

    NewsContentDTO result = service.updateContent(content);

    Assert.assertEquals(content, result);

    BDDMockito.verify(service, BDDMockito.times(1)).dealWithContentToUpdate(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateImage_Null() throws Exception {
    String result = service.createImage(null);

    Assert.assertEquals("", result);

    BDDMockito.verify(newsImageService, BDDMockito.times(0)).createEntity(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateImage_Not_Null() throws Exception {

    NewsImageDTO formattingImage = NewsImageDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(formattingImage).when(newsImageService).createEntity(BDDMockito.any(NewsImageDTO.class));

    String result = service.createImage(formattingImage);

    Assert.assertEquals(String.valueOf(1l), result);

    BDDMockito.verify(newsImageService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsImageDTO.class));
  }

  @Test
  public void testCreateContent_Null() throws Exception {
    String result = service.createContent(null);

    Assert.assertEquals("", result);

    BDDMockito.verify(newsContentService, BDDMockito.times(0)).createEntity(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testCreateContent_Not_Null() throws Exception {

    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();

    BDDMockito.doReturn(content).when(newsContentService).createEntity(BDDMockito.eq(content));

    String result = service.createContent(content);

    Assert.assertEquals(String.valueOf(1l), result);

    BDDMockito.verify(newsContentService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsContentDTO.class));
  }

  @Test
  public void testUpdateEntity_No_Image_No_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.nullable(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_No_Image_Content() throws Exception {

    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsContent(content).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = NewsImageDTOBuilder.create().id(1L).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(image).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(image).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.nullable(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testUpdateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = NewsImageDTOBuilder.create().id(1L).build();
    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(image).newsContent(content).id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(content).when(service).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(image).when(service).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.updateEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNotNull(result.getNewsContent());
    Assert.assertEquals(content.getId(), result.getNewsContent().getId());
    Assert.assertNotNull(result.getNewsImage());
    Assert.assertEquals(image.getId(), result.getNewsImage().getId());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).updateImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.nullable(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_No_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsImageDTO image = NewsImageDTOBuilder.create().id(1L).build();
    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsImage(image).newsContent(content).id(1L).build();
    NewsEntryDTO returnEntry = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn("").when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.any(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_No_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsContentDTO content = NewsContentDTOBuilder.create().id(1L).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().newsContent(content).id(1L).build();
    NewsEntryDTO returnEntry = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(String.valueOf(1L)).when(service).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(returnEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.any(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testCreateEntity_Image_Content() throws Exception {
    NewsEntry entry = new NewsEntry();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().id(1L).build();

    BDDMockito.doNothing().when(service).fillObject(BDDMockito.eq(newsEntry), BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(entry).when(repository).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.doReturn(newsEntry).when(service).toDTO(BDDMockito.any(NewsEntry.class));

    NewsEntryDTO result = service.createEntity(newsEntry);

    Assert.assertEquals(newsEntry.getId(), result.getId());
    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(service, BDDMockito.times(1)).fillObject(BDDMockito.any(NewsEntryDTO.class),
        BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createContent(BDDMockito.nullable(NewsContentDTO.class));
    BDDMockito.verify(service, BDDMockito.times(1)).createImage(BDDMockito.nullable(NewsImageDTO.class));
    BDDMockito.verify(repository, BDDMockito.times(1)).save(BDDMockito.any(NewsEntry.class));
    BDDMockito.verify(service, BDDMockito.times(1)).toDTO(BDDMockito.any(NewsEntry.class));
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_True() throws Exception {
    NewsEntryDTO entry = NewsEntryDTOBuilder.create().build();

    BDDMockito.doReturn(Arrays.asList(entry)).when(repository).findByFacebookId(BDDMockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyImportedFromFacebook_False() throws Exception {

    BDDMockito.doReturn(Arrays.asList()).when(repository).findByFacebookId(BDDMockito.anyString());

    boolean result = service.isAlreadyImportedFromFacebook("123456789");

    Assert.assertFalse(result);
  }

}
