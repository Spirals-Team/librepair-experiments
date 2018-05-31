package com.cmpl.web.core.news;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryTranslatorImplTest {

  @InjectMocks
  @Spy
  private NewsEntryTranslatorImpl translator;

  @Test
  public void testFromImageRequestToDTO() throws Exception {

    LocalDateTime date = LocalDateTime.now();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().alt("someAlt").legend("someLegend")
        .creationDate(date).modificationDate(date).id(1L).build();

    NewsImageDTO result = translator.fromImageRequestToDTO(imageRequest);

    Assert.assertEquals(imageRequest.getAlt(), result.getAlt());
    Assert.assertEquals(imageRequest.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(imageRequest.getId(), result.getId());
    Assert.assertEquals(imageRequest.getLegend(), result.getLegend());
    Assert.assertEquals(imageRequest.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromContentRequestToDTO() throws Exception {
    LocalDateTime date = LocalDateTime.now();
    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().content("someContent").creationDate(date)
        .modificationDate(date).id(1L).build();

    NewsContentDTO result = translator.fromContentRequestToDTO(contentRequest);

    Assert.assertEquals(contentRequest.getContent(), result.getContent());
    Assert.assertEquals(contentRequest.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(contentRequest.getId(), result.getId());
    Assert.assertEquals(contentRequest.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromDTOToResponse() throws Exception {

    NewsEntryDTO newsEntryDTO = new NewsEntryDTO();

    NewsEntryResponse result = translator.fromDTOToResponse(newsEntryDTO);

    Assert.assertEquals(newsEntryDTO, result.getNewsEntry());
  }

  @Test
  public void testFromRequestToDTO_No_Image_No_Content() throws Exception {

    LocalDateTime date = LocalDateTime.now();
    NewsEntryRequest request = NewsEntryRequestBuilder.create().author("someAuthor").title("someTitle")
        .tags("someTags").id(1L).creationDate(date).modificationDate(date).build();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertNull(result.getNewsImage());
    Assert.assertNull(result.getNewsContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());

  }

  @Test
  public void testFromRequestToDTO_No_Image_Content() throws Exception {
    LocalDateTime date = LocalDateTime.now();
    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().content("someContent").creationDate(date)
        .modificationDate(date).id(1L).build();
    NewsEntryRequest request = NewsEntryRequestBuilder.create().author("someAuthor").title("someTitle")
        .tags("someTags").content(contentRequest).id(1L).creationDate(date).modificationDate(date).build();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertNull(result.getNewsImage());
    Assert.assertEquals(contentRequest.getContent(), result.getNewsContent().getContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromRequestToDTO_Image_No_Content() throws Exception {

    LocalDateTime date = LocalDateTime.now();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().alt("someAlt").legend("someLegend")
        .creationDate(date).modificationDate(date).id(1L).build();
    NewsEntryRequest request = NewsEntryRequestBuilder.create().author("someAuthor").title("someTitle")
        .tags("someTags").image(imageRequest).id(1L).creationDate(date).modificationDate(date).build();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertNull(result.getNewsContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }

  @Test
  public void testFromRequestToDTO_With_Image_With_Content() throws Exception {
    LocalDateTime date = LocalDateTime.now();
    NewsContentRequest contentRequest = NewsContentRequestBuilder.create().content("someContent").creationDate(date)
        .modificationDate(date).id(1L).build();
    NewsImageRequest imageRequest = NewsImageRequestBuilder.create().alt("someAlt").legend("someLegend")
        .creationDate(date).modificationDate(date).id(1L).build();
    NewsEntryRequest request = NewsEntryRequestBuilder.create().author("someAuthor").title("someTitle")
        .tags("someTags").image(imageRequest).content(contentRequest).id(1L).creationDate(date).modificationDate(date)
        .build();

    NewsEntryDTO result = translator.fromRequestToDTO(request);

    Assert.assertEquals(request.getAuthor(), result.getAuthor());
    Assert.assertEquals(request.getTags(), result.getTags());
    Assert.assertEquals(request.getTitle(), result.getTitle());
    Assert.assertEquals(contentRequest.getContent(), result.getNewsContent().getContent());
    Assert.assertEquals(request.getId(), result.getId());
    Assert.assertEquals(request.getCreationDate(), result.getCreationDate());
    Assert.assertEquals(request.getModificationDate(), result.getModificationDate());
  }
}
