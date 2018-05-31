package com.cmpl.web.facebook;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.news.NewsEntryService;

;

@RunWith(MockitoJUnitRunner.class)
public class FacebookServiceImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private SimpleDateFormat dateFormat;

  @Mock
  private ConnectionRepository connectionRepository;

  @Mock
  private ContextHolder contextHolder;

  @InjectMocks
  @Spy
  private FacebookServiceImpl facebookService;

  @Test
  public void testComputeDescription_From_Message() throws Exception {
    String message = "someMessage";
    Post post = PostBuilder.create().message(message).build();

    String result = facebookService.computeDescription(post);

    Assert.assertEquals(message, result);

  }

  @Test
  public void testComputeDescription_From_Description() throws Exception {
    String description = "someDescription";
    Post post = PostBuilder.create().description(description).build();

    String result = facebookService.computeDescription(post);

    Assert.assertEquals(description, result);

  }

  @Test
  public void testComputeOnclick() throws Exception {
    String toggle = "toggleImport('123456789')";

    String id = "123456789";
    Post post = PostBuilder.create().id(id).build();

    BDDMockito.doReturn("123456789").when(facebookService).computeId(BDDMockito.eq(post));

    String result = facebookService.computeOnclick(post);

    Assert.assertEquals(toggle, result);
  }

  @Test
  public void testComputeId() throws Exception {
    String id = "123456789";
    Post post = PostBuilder.create().id(id).build();

    String result = facebookService.computeId(post);

    Assert.assertEquals(id, result);
  }

  @Test
  public void testComputeObjectId() throws Exception {
    String objectId = "123456789";
    Post post = PostBuilder.create().objectId(objectId).build();

    String result = facebookService.computeObjectId(post);

    Assert.assertEquals(objectId, result);
  }

  @Test
  public void testComputeCreatedTime() throws Exception {
    ZoneId defaultZoneId = ZoneId.systemDefault();
    LocalDate createdTime = LocalDate.now();
    Post post = PostBuilder.create().createdTime(Date.from(createdTime.atStartOfDay(defaultZoneId).toInstant()))
        .build();

    LocalDate result = facebookService.computeCreatedTime(post);

    Assert.assertEquals(createdTime, result);
  }

  @Test
  public void testComputeType() throws Exception {
    PostType type = PostType.STATUS;
    Post post = PostBuilder.create().type(type).build();

    PostType result = facebookService.computeType(post);

    Assert.assertEquals(type, result);
  }

  @Test
  public void testComputeLink() throws Exception {
    String link = "someLink";
    Post post = PostBuilder.create().link(link).build();

    String result = facebookService.computeLink(post);

    Assert.assertEquals(link, result);
  }

  @Test
  public void testComputeAuthor() throws Exception {
    String name = "someName";
    Reference reference = ReferenceBuilder.create().name(name).build();
    Post post = PostBuilder.create().reference(reference).build();

    String result = facebookService.computeAuthor(post);

    Assert.assertEquals(name, result);

  }

  @Test
  public void testComputePhotoUrl_Ok() throws Exception {

    String picture = "somePictureUrl";
    Post post = PostBuilder.create().picture(picture).type(PostType.PHOTO).build();

    String result = facebookService.computePhotoUrl(post);

    Assert.assertEquals(picture, result);
  }

  @Test
  public void testComputePhotoUrl_No_Photo() throws Exception {
    Post post = PostBuilder.create().type(PostType.STATUS).build();

    String result = facebookService.computePhotoUrl(post);

    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testComputeVideoUrl_Ok() throws Exception {
    String sourceNotAutoplay = "someVideoUrl?autoplay=0";
    String sourceAutoplay = "someVideoUrl?autoplay=1";
    Post post = PostBuilder.create().source(sourceAutoplay).build();

    BDDMockito.doReturn(sourceNotAutoplay).when(facebookService).makeVideoNotAutoplay(BDDMockito.eq(sourceAutoplay));
    String result = facebookService.computeVideoUrl(post);

    Assert.assertEquals(sourceNotAutoplay, result);

  }

  @Test
  public void testComputeVideoUrl_No_Video() throws Exception {

    Post post = PostBuilder.create().build();

    String result = facebookService.computeVideoUrl(post);

    Assert.assertNull(result);
  }

  @Test
  public void testMakeVideoNotAutoplay_Change() throws Exception {
    String sourceNotAutoplay = "someVideoUrl?autoplay=0";
    String sourceAutoplay = "someVideoUrl?autoplay=1";

    String result = facebookService.makeVideoNotAutoplay(sourceAutoplay);

    Assert.assertEquals(sourceNotAutoplay, result);
  }

  @Test
  public void testMakeVideoNotAutoplay_No_Change() throws Exception {

    String source = "someVideoUrl";

    String result = facebookService.makeVideoNotAutoplay(source);

    Assert.assertEquals(source, result);
  }

  @Test
  public void testComputeTitle_From_Name() throws Exception {
    String name = "someName";
    Post post = PostBuilder.create().name(name).build();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(name, result);
  }

  @Test
  public void testComputeTitle_From_Caption() throws Exception {
    String caption = "someCaption";
    Post post = PostBuilder.create().caption(caption).build();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(caption, result);
  }

  @Test
  public void testComputeTitle_From_Type() throws Exception {
    String type = "Type STATUS";
    Post post = PostBuilder.create().type(PostType.STATUS).build();

    String result = facebookService.computeTitle(post);

    Assert.assertEquals(type, result);
  }

  @Test
  public void testComputeImportablePost() throws Exception {
    String message = "someMessage";
    String caption = "someCaption";
    String name = "someName";
    String source = "someVideoUrl";
    String picture = "somePictureUrl";
    String link = "someLink";
    PostType type = PostType.STATUS;
    String objectId = "123456789";
    String id = "123456789";
    String description = "someDescription";
    String author = "author";
    LocalDate createdTime = LocalDate.now();
    String formattedDate = "24/10/89";
    String onclick = "toggleImport(" + id + ");";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    ZoneId defaultZoneId = ZoneId.systemDefault();

    Post post = PostBuilder.create().id(id).objectId(objectId).name(name).caption(caption).description(description)
        .source(source).message(message).picture(picture).link(link).type(type)
        .createdTime(Date.from(createdTime.atStartOfDay(defaultZoneId).toInstant())).build();

    BDDMockito.doReturn(author).when(facebookService).computeAuthor(BDDMockito.eq(post));
    BDDMockito.doReturn(description).when(facebookService).computeDescription(BDDMockito.eq(post));
    BDDMockito.doReturn(picture).when(facebookService).computePhotoUrl(BDDMockito.eq(post));
    BDDMockito.doReturn(link).when(facebookService).computeLink(BDDMockito.eq(post));
    BDDMockito.doReturn(source).when(facebookService).computeVideoUrl(BDDMockito.eq(post));
    BDDMockito.doReturn(name).when(facebookService).computeTitle(BDDMockito.eq(post));
    BDDMockito.doReturn(type).when(facebookService).computeType(BDDMockito.eq(post));
    BDDMockito.doReturn(id).when(facebookService).computeId(BDDMockito.eq(post));
    BDDMockito.doReturn(onclick).when(facebookService).computeOnclick(BDDMockito.eq(post));
    BDDMockito.doReturn(createdTime).when(facebookService).computeCreatedTime(BDDMockito.eq(post));
    BDDMockito.doReturn(objectId).when(facebookService).computeObjectId(BDDMockito.eq(post));
    BDDMockito.doReturn(formattedDate).when(facebookService)
        .computeFormattedDate(BDDMockito.eq(post), BDDMockito.eq(formatter));

    ImportablePost result = facebookService.computeImportablePost(post, formatter);

    Assert.assertEquals(author, result.getAuthor());
    Assert.assertEquals(description, result.getDescription());
    Assert.assertEquals(picture, result.getPhotoUrl());
    Assert.assertEquals(link, result.getLinkUrl());
    Assert.assertEquals(source, result.getVideoUrl());
    Assert.assertEquals(name, result.getTitle());
    Assert.assertEquals(type, result.getType());
    Assert.assertEquals(id, result.getFacebookId());
    Assert.assertEquals(onclick, result.getOnclick());
    Assert.assertEquals(createdTime, result.getCreationDate());
    Assert.assertEquals(objectId, result.getObjectId());
    Assert.assertEquals(formattedDate, result.getFormattedDate());

    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeAuthor(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeDescription(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computePhotoUrl(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeLink(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeVideoUrl(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeTitle(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeType(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeId(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeOnclick(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeCreatedTime(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeObjectId(BDDMockito.eq(post));
    BDDMockito.verify(facebookService, BDDMockito.times(1)).computeCreatedTime(BDDMockito.eq(post));

  }

  @Test
  public void testComputeFormattedDate() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    Calendar calendar = Calendar.getInstance();
    calendar.set(2017, 10, 15);
    Date date = calendar.getTime();

    Post post = PostBuilder.create().createdTime(date).build();

    String result = facebookService.computeFormattedDate(post, formatter);

    Assert.assertEquals("15/11/17", result);

  }

  @Test
  public void testCanImportPost_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.STATUS).description("someDescription")
        .facebookId("someId").build();

    BDDMockito.doReturn(false).when(newsEntryService).isAlreadyImportedFromFacebook(BDDMockito.anyString());

    boolean result = facebookService.canImportPost(post);

    Assert.assertTrue(result);
  }

  @Test
  public void testCanImportPost_Ko_Status_And_Empty_Description() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.STATUS).facebookId("someId").build();

    boolean result = facebookService.canImportPost(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testCanImportPost_Ko_Already_Imported() throws Exception {

    ImportablePost post = new ImportablePostBuilder().type(PostType.VIDEO).description("someDescription")
        .facebookId("someId").build();

    BDDMockito.doReturn(true).when(newsEntryService).isAlreadyImportedFromFacebook(BDDMockito.anyString());

    boolean result = facebookService.canImportPost(post);

    Assert.assertFalse(result);
  }

  @Test
  public void testGetFeedOperations() throws Exception {

    FeedOperations operations = BDDMockito.mock(FeedOperations.class);

    BDDMockito.doReturn(operations).when(facebookConnector).feedOperations();

    FeedOperations result = facebookService.getFeedOperations();

    Assert.assertEquals(operations, result);
  }

  @Test
  public void testComputeImportablePosts_Importable() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    Post postToImport = PostBuilder.create().build();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Arrays.asList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().build();

    BDDMockito.doReturn(formatter).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(importable).when(facebookService)
        .computeImportablePost(BDDMockito.eq(postToImport), BDDMockito.any(DateTimeFormatter.class));
    BDDMockito.doReturn(true).when(facebookService).canImportPost(BDDMockito.eq(importable));

    List<ImportablePost> postsImportable = facebookService.computeImportablePosts(postsToImport);

    Assert.assertTrue(!CollectionUtils.isEmpty(postsImportable));
    Assert.assertEquals(importable, postsImportable.get(0));

  }

  @Test
  public void testComputeImportablePosts_Already_Imported() throws Exception {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    Post postToImport = PostBuilder.create().build();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Arrays.asList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().build();

    BDDMockito.doReturn(formatter).when(contextHolder).getDateFormat();
    BDDMockito.doReturn(importable).when(facebookService)
        .computeImportablePost(BDDMockito.eq(postToImport), BDDMockito.any(DateTimeFormatter.class));
    BDDMockito.doReturn(false).when(facebookService).canImportPost(BDDMockito.eq(importable));

    List<ImportablePost> postsImportable = facebookService.computeImportablePosts(postsToImport);

    Assert.assertTrue(CollectionUtils.isEmpty(postsImportable));

  }

  @Test
  public void testGetRecentFeed_Ok() throws Exception {

    @SuppressWarnings("unchecked")
    Connection<Facebook> connection = (Connection<Facebook>) BDDMockito.mock(Connection.class);
    FeedOperations operations = BDDMockito.mock(FeedOperations.class);

    Post postToImport = PostBuilder.create().build();
    PagingParameters previous = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagingParameters next = new PagingParameters(0, 0, 0L, 0L, "after", "before");
    PagedList<Post> postsToImport = new PagedList<>(Arrays.asList(postToImport), previous, next);

    ImportablePost importable = new ImportablePostBuilder().build();
    List<ImportablePost> importables = Arrays.asList(importable);

    BDDMockito.doReturn(connection).when(connectionRepository).findPrimaryConnection(Facebook.class);
    BDDMockito.doReturn(operations).when(facebookConnector).feedOperations();
    BDDMockito.doReturn(postsToImport).when(operations).getPosts();
    BDDMockito.doReturn(importables).when(facebookService).computeImportablePosts(BDDMockito.eq(postsToImport));

    List<ImportablePost> result = facebookService.getRecentFeed();

    Assert.assertEquals(importables, result);

  }

  @Test
  public void testGetRecentFeed_Exception() throws Exception {
    exception.expect(BaseException.class);
    facebookService.getRecentFeed();
  }
}
