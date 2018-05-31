package com.cmpl.web.facebook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.news.NewsContentDTO;
import com.cmpl.web.core.news.NewsContentDTOBuilder;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryDTOBuilder;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.news.NewsImageDTO;
import com.cmpl.web.core.news.NewsImageDTOBuilder;
import com.google.api.client.util.IOUtils;

@RunWith(MockitoJUnitRunner.class)
public class FacebookImportServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private FacebookAdapter facebookAdapter;

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private FacebookImportServiceImpl facebookImport;

  @Test
  public void testComputeContentTypeFromBytes() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(BDDMockito.eq(data));
    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId("123456789").build();
    String result = facebookImport.computeContentTypeFromBytes(post, data);

    Assert.assertEquals("image/jpeg", result);
  }

  @Test
  public void testComputeContentTypeFromBytes_Null() throws Exception {

    byte[] data = new byte[]{1};
    ByteArrayInputStream is = new ByteArrayInputStream(data);

    BDDMockito.doReturn(is).when(facebookImport).prepareInputStream(BDDMockito.eq(data));
    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId("123456789").build();
    String result = facebookImport.computeContentTypeFromBytes(post, data);

    Assert.assertNull(result);
  }

  @Test
  public void testPrepareInputStream() throws Exception {
    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    ByteArrayInputStream is = new ByteArrayInputStream(data);
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    ByteArrayInputStream result = facebookImport.prepareInputStream(data);
    ByteArrayOutputStream resultOs = new ByteArrayOutputStream();

    IOUtils.copy(is, os);
    IOUtils.copy(result, resultOs);

    Assert.assertEquals(os.toString(), resultOs.toString());

  }

  @Test
  public void testGetMediaOperations() throws Exception {

    MediaOperations operations = BDDMockito.mock(MediaOperations.class);

    BDDMockito.doReturn(operations).when(facebookAdapter).getMediaOperations();

    MediaOperations result = facebookImport.getMediaOperations();

    Assert.assertEquals(operations, result);
  }

  @Test
  public void testRecoverImageBytes_Ok() throws Exception {

    String path = "src/test/resources/img/logo.jpg";
    byte[] data = Files.readAllBytes(Paths.get(path));
    MediaOperations operations = BDDMockito.mock(MediaOperations.class);

    FacebookImportPost post = FacebookImportPostBuilder.create().objectId("123456789").build();

    BDDMockito.doReturn(data).when(operations).getAlbumImage(BDDMockito.anyString(), BDDMockito.any(ImageType.class));
    BDDMockito.doReturn(operations).when(facebookAdapter).getMediaOperations();

    byte[] result = facebookImport.recoverImageBytes(post);

    Assert.assertEquals(data, result);

  }

  @Test
  public void testComputeTitle() throws Exception {

    String title = "someTitle";
    BDDMockito.doReturn(title).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.news.title"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeTitle(Locale.FRANCE);

    Assert.assertEquals(title, result);
  }

  @Test
  public void testComputeTags() throws Exception {

    String tags = "someTags";
    BDDMockito.doReturn(tags).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.news.tag"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeTags(Locale.FRANCE);

    Assert.assertEquals(tags, result);
  }

  @Test
  public void testHasImage_True() throws Exception {
    FacebookImportPost post = FacebookImportPostBuilder.create().photoUrl("someUrl").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasImage_False_Null() throws Exception {

    FacebookImportPost post = FacebookImportPostBuilder.create().build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Empty() throws Exception {

    FacebookImportPost post = FacebookImportPostBuilder.create().photoUrl("").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasImage_False_Whitespaces() throws Exception {

    FacebookImportPost post = FacebookImportPostBuilder.create().photoUrl(" ").build();

    boolean result = facebookImport.hasImage(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_True() throws Exception {
    FacebookImportPost post = FacebookImportPostBuilder.create().description("someDescription").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testHasContent_False_Null() throws Exception {

    FacebookImportPost post = FacebookImportPostBuilder.create().build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Empty() throws Exception {
    FacebookImportPost post = FacebookImportPostBuilder.create().description("").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testHasContent_False_Whitespaces() throws Exception {
    FacebookImportPost post = FacebookImportPostBuilder.create().description("   ").build();

    boolean result = facebookImport.hasContent(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeAlt() throws Exception {
    String alt = "someAlt";
    String facebookId = "123456789";
    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).build();
    BDDMockito.doReturn(alt).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.image.alt"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeAlt(post, Locale.FRANCE);

    Assert.assertEquals(alt + facebookId, result);
  }

  @Test
  public void testComputeLegend() throws Exception {
    String legend = "someLegend";
    BDDMockito.doReturn(legend).when(messageSource)
        .getI18n(BDDMockito.eq("facebook.image.legend"), BDDMockito.eq(Locale.FRANCE));

    String result = facebookImport.computeLegend(Locale.FRANCE);

    Assert.assertEquals(legend, result);
  }

  @Test
  public void testComputeNewsContentFromPost() throws Exception {
    String content = "someContent";
    String linkUrl = "linkUrl";
    String videoUrl = "videoUrl";
    FacebookImportPost post = FacebookImportPostBuilder.create().description(content).videoUrl(videoUrl)
        .linkUrl(linkUrl).build();

    NewsContentDTO result = facebookImport.computeNewsContentFromPost(post);

    Assert.assertEquals(content, result.getContent());
    Assert.assertEquals(linkUrl, result.getLinkUrl());
    Assert.assertEquals(videoUrl, result.getVideoUrl());
  }

  @Test
  public void testComputeNewsImageFromPost() throws Exception {
    String alt = "someAlt";
    String legend = "someLegend";
    FacebookImportPost post = FacebookImportPostBuilder.create().build();

    BDDMockito.doReturn(alt).when(facebookImport).computeAlt(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(legend).when(facebookImport).computeLegend(BDDMockito.eq(Locale.FRANCE));
    NewsImageDTO result = facebookImport.computeNewsImageFromPost(post, Locale.FRANCE);

    Assert.assertEquals(alt, result.getAlt());
    Assert.assertEquals(legend, result.getLegend());
  }

  @Test
  public void testConvertPostToNewsEntry_Content_Image() throws Exception {
    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).author(author).build();
    NewsContentDTO content = NewsContentDTOBuilder.create().build();
    NewsImageDTO image = NewsImageDTOBuilder.create().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(BDDMockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport)
        .computeNewsImageFromPost(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertEquals(image, result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));

  }

  @Test
  public void testConvertPostToNewsEntry_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).author(author).build();
    NewsContentDTO content = NewsContentDTOBuilder.create().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(content).when(facebookImport).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(BDDMockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(content, result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).author(author).build();
    NewsImageDTO image = NewsImageDTOBuilder.create().build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(true).when(facebookImport).hasImage(BDDMockito.eq(post));
    BDDMockito.doReturn(image).when(facebookImport)
        .computeNewsImageFromPost(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertEquals(image, result.getNewsImage());
    Assert.assertNull(result.getNewsContent());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testConvertPostToNewsEntry_No_Content_No_Image() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";
    String tags = "someTags";
    String title = "someTitle";

    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).author(author).build();

    BDDMockito.doReturn(tags).when(facebookImport).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(facebookImport).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookImport).hasContent(BDDMockito.eq(post));
    BDDMockito.doReturn(false).when(facebookImport).hasImage(BDDMockito.eq(post));

    NewsEntryDTO result = facebookImport.convertPostToNewsEntry(post, Locale.FRANCE);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(facebookId, result.getFacebookId());
    Assert.assertEquals(tags, result.getTags());
    Assert.assertEquals(title, result.getTitle());

    Assert.assertNull(result.getNewsContent());
    Assert.assertNull(result.getNewsImage());

    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTags(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).computeTitle(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsContentFromPost(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(0)).computeNewsImageFromPost(BDDMockito.eq(post),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasImage(BDDMockito.eq(post));
    BDDMockito.verify(facebookImport, BDDMockito.times(1)).hasContent(BDDMockito.eq(post));
  }

  @Test
  public void testImportFacebookPost() throws Exception {

    String facebookId = "123456789";
    String author = "someAuthor";

    FacebookImportPost post = FacebookImportPostBuilder.create().facebookId(facebookId).author(author).build();
    NewsEntryDTO newsEntry = NewsEntryDTOBuilder.create().build();

    BDDMockito.doReturn(newsEntry).when(newsEntryService).createEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.doReturn(newsEntry).when(facebookImport)
        .convertPostToNewsEntry(BDDMockito.eq(post), BDDMockito.eq(Locale.FRANCE));

    List<NewsEntryDTO> result = facebookImport.importFacebookPost(Arrays.asList(post), Locale.FRANCE);

    Assert.assertTrue(result.size() == 1);
    Assert.assertEquals(newsEntry, result.get(0));
  }
}
