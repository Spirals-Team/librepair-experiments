package com.cmpl.web.core.news;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.ERROR_TYPE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;
import com.cmpl.web.core.common.message.WebMessageSource;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryRequestValidatorImplTest {

  @Mock
  private WebMessageSource messageSource;

  @InjectMocks
  @Spy
  private NewsEntryRequestValidatorImpl validator;

  @Test
  public void testIsNewsContentValid_True() throws Exception {

    NewsContentRequest request = NewsContentRequestBuilder.create().content("someString").build();
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testIsNewsContentValid_False_null() throws Exception {
    NewsContentRequest request = NewsContentRequestBuilder.create().build();

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsContentValid_False_empty() throws Exception {
    NewsContentRequest request = NewsContentRequestBuilder.create().content("").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsContentValid_False_blank() throws Exception {
    NewsContentRequest request = NewsContentRequestBuilder.create().content(" ").build();
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.anyString());

    boolean result = validator.isNewsContentValid(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testIsNewsImageValid_True_With_Request() {

    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt("someAlt");
    imageRequest.setLegend("someLegend");

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.anyString());

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsImageValid_True_Without_Request() {

    List<ErrorCause> result = validator.isNewsImageValid(null, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_Src_Legend() {

    String alt = "alt";
    String legend = "legend";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(legend));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyAlt.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_No_Src_Legend() {

    String alt = "alt";
    String legend = "legend";
    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(legend));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";

    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(legend));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));
    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_No_Alt_No_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";

    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptySrc = new ErrorCause();
    errorCauseEmptySrc.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptySrc.setMessage("someMessage1");

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(legend));

    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));

  }

  @Test
  public void testIsNewsImageValid_False_Alt_No_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";

    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    ErrorCause errorCauseEmptyAlt = new ErrorCause();
    errorCauseEmptyAlt.setCode(ERROR_CAUSE.EMPTY_NEWS_ALT.toString());
    errorCauseEmptyAlt.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(legend));
    BDDMockito.doReturn(errorCauseEmptyAlt).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_ALT), BDDMockito.eq(Locale.FRANCE));

    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertTrue(result.contains(errorCauseEmptyLegend));

    Assert.assertTrue(result.contains(errorCauseEmptyAlt));
  }

  @Test
  public void testIsNewsImageValid_False_Alt_Src_No_Legend() {

    String alt = "alt";
    String legend = "legend";

    NewsImageRequest imageRequest = new NewsImageRequest();
    imageRequest.setAlt(alt);
    imageRequest.setLegend(legend);

    ErrorCause errorCauseEmptyLegend = new ErrorCause();
    errorCauseEmptyLegend.setCode(ERROR_CAUSE.EMPTY_NEWS_LEGEND.toString());
    errorCauseEmptyLegend.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(alt));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(legend));
    BDDMockito.doReturn(errorCauseEmptyLegend).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_LEGEND), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsImageValid(imageRequest, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyLegend.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_True_No_Entry() {

    List<ErrorCause> result = validator.isNewsEntryValid(null, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsEntryValid_True() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(title));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(author));

    List<ErrorCause> result = validator.isNewsEntryValid(request, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testIsNewsEntryValid_False_No_Title() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyTitle = new ErrorCause();
    errorCauseEmptyTitle.setCode(ERROR_CAUSE.EMPTY_NEWS_TITLE.toString());
    errorCauseEmptyTitle.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(title));
    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyTitle).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_TITLE), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsEntryValid(request, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyTitle.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_False_No_Author() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyAuhtor = new ErrorCause();
    errorCauseEmptyAuhtor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuhtor.setMessage("someMessage1");

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(title));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyAuhtor).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_AUTHOR), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsEntryValid(request, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyAuhtor.getCode(), result.get(0).getCode());
  }

  @Test
  public void testIsNewsEntryValid_False_No_Title_No_Author() {

    String title = "title";
    String author = "author";

    NewsEntryRequest request = new NewsEntryRequest();
    request.setTitle(title);
    request.setAuthor(author);

    ErrorCause errorCauseEmptyTitle = new ErrorCause();
    errorCauseEmptyTitle.setCode(ERROR_CAUSE.EMPTY_NEWS_TITLE.toString());
    errorCauseEmptyTitle.setMessage("someMessage1");

    ErrorCause errorCauseEmptyAuhtor = new ErrorCause();
    errorCauseEmptyAuhtor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuhtor.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(title));
    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(author));
    BDDMockito.doReturn(errorCauseEmptyTitle).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_TITLE), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(errorCauseEmptyAuhtor).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_AUTHOR), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isNewsEntryValid(request, Locale.FRANCE);

    Assert.assertTrue(result.contains(errorCauseEmptyTitle));
    Assert.assertTrue(result.contains(errorCauseEmptyTitle));
  }

  @Test
  public void testIsContentValid_True_No_Content() {

    List<ErrorCause> result = validator.isContentValid(null, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsContentValid_True_Content() {

    String content = "content";
    NewsContentRequest contentRequest = new NewsContentRequest();
    contentRequest.setContent(content);

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(content));

    List<ErrorCause> result = validator.isContentValid(contentRequest, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsContentValid_False() {

    String content = "content";
    NewsContentRequest contentRequest = new NewsContentRequest();
    contentRequest.setContent(content);

    ErrorCause errorCauseEmptyContent = new ErrorCause();
    errorCauseEmptyContent.setCode(ERROR_CAUSE.EMPTY_NEWS_CONTENT.toString());
    errorCauseEmptyContent.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(content));
    BDDMockito.doReturn(errorCauseEmptyContent).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_CONTENT), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isContentValid(contentRequest, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyContent.getCode(), result.get(0).getCode());

  }

  @Test
  public void testIsIdValid_True() {

    String id = "id";

    BDDMockito.doReturn(true).when(validator).isStringValid(BDDMockito.eq(id));

    List<ErrorCause> result = validator.isIdValid(id, Locale.FRANCE);

    Assert.assertTrue(CollectionUtils.isEmpty(result));

  }

  @Test
  public void testIsIdValid_False_EmptyId() {

    String id = "";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");

    BDDMockito.doReturn(false).when(validator).isStringValid(BDDMockito.eq(id));
    BDDMockito.doReturn(errorCauseEmptyNewsId).when(validator)
        .computeCause(BDDMockito.eq(ERROR_CAUSE.EMPTY_NEWS_ID), BDDMockito.eq(Locale.FRANCE));

    List<ErrorCause> result = validator.isIdValid(id, Locale.FRANCE);

    Assert.assertEquals(errorCauseEmptyNewsId.getCode(), result.get(0).getCode());

  }

  @Test
  public void testValidateGet_Ok() {

    String id = "id";

    BDDMockito.doReturn(Arrays.asList()).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));

    Error result = validator.validateGet(id, Locale.FRANCE);
    Assert.assertNull(result);

  }

  @Test
  public void testValidateGet_Ko() {

    String id = "id";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");
    List<ErrorCause> causes = Arrays.asList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());

    BDDMockito.doReturn(causes).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.eq(causes));

    Error result = validator.validateGet(id, Locale.FRANCE);

    Assert.assertEquals(ERROR_TYPE.INVALID_REQUEST.toString(), result.getCode());
    Assert.assertEquals(errorCauseEmptyNewsId, result.getCauses().get(0));

  }

  @Test
  public void testValidateDelete_Ok() {

    String id = "id";

    BDDMockito.doReturn(Arrays.asList()).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));

    Error result = validator.validateDelete(id, Locale.FRANCE);
    Assert.assertNull(result);

  }

  @Test
  public void testValidateDelete_Ko() {

    String id = "id";

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");
    List<ErrorCause> causes = Arrays.asList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());

    BDDMockito.doReturn(causes).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.eq(causes));

    Error result = validator.validateDelete(id, Locale.FRANCE);

    Assert.assertEquals(ERROR_TYPE.INVALID_REQUEST.toString(), result.getCode());
    Assert.assertEquals(errorCauseEmptyNewsId, result.getCauses().get(0));

  }

  @Test
  public void testValidateUpdate_Ok() {

    String id = "id";
    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(Arrays.asList()).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsEntryValid(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isContentValid(BDDMockito.eq(content), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsImageValid(BDDMockito.eq(image), BDDMockito.eq(Locale.FRANCE));

    Error result = validator.validateUpdate(request, id, Locale.FRANCE);

    Assert.assertNull(result);
    BDDMockito.verify(validator, BDDMockito.times(1)).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsEntryValid(BDDMockito.eq(request),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isContentValid(BDDMockito.eq(content),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsImageValid(BDDMockito.eq(image),
        BDDMockito.eq(Locale.FRANCE));

  }

  @Test
  public void testValidateUpdate_Ko_EmptyId() {

    String id = "id";
    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    ErrorCause errorCauseEmptyNewsId = new ErrorCause();
    errorCauseEmptyNewsId.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCauseEmptyNewsId.setMessage("someMessage1");

    List<ErrorCause> causes = Arrays.asList(errorCauseEmptyNewsId);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(causes).when(validator).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsEntryValid(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isContentValid(BDDMockito.eq(content), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsImageValid(BDDMockito.eq(image), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Error result = validator.validateUpdate(request, id, Locale.FRANCE);

    Assert.assertNotNull(result);
    BDDMockito.verify(validator, BDDMockito.times(1)).isIdValid(BDDMockito.eq(id), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsEntryValid(BDDMockito.eq(request),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isContentValid(BDDMockito.eq(content),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsImageValid(BDDMockito.eq(image),
        BDDMockito.eq(Locale.FRANCE));

  }

  @Test
  public void testValidateCreate_Ok() {

    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsEntryValid(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isContentValid(BDDMockito.eq(content), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsImageValid(BDDMockito.eq(image), BDDMockito.eq(Locale.FRANCE));

    Error result = validator.validateCreate(request, Locale.FRANCE);

    Assert.assertNull(result);
    BDDMockito.verify(validator, BDDMockito.times(0)).isIdValid(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsEntryValid(BDDMockito.eq(request),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isContentValid(BDDMockito.eq(content),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsImageValid(BDDMockito.eq(image),
        BDDMockito.eq(Locale.FRANCE));

  }

  @Test
  public void testValidateCreate_Ko_EMPTY_NEWS_AUTHOR() {

    NewsEntryRequest request = new NewsEntryRequest();
    NewsContentRequest content = new NewsContentRequest();
    NewsImageRequest image = new NewsImageRequest();

    ErrorCause errorCauseEmptyAuthor = new ErrorCause();
    errorCauseEmptyAuthor.setCode(ERROR_CAUSE.EMPTY_NEWS_AUTHOR.toString());
    errorCauseEmptyAuthor.setMessage("someMessage1");

    List<ErrorCause> causes = Arrays.asList(errorCauseEmptyAuthor);

    Error error = new Error();
    error.setCauses(causes);
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());

    request.setContent(content);
    request.setImage(image);

    BDDMockito.doReturn(causes).when(validator).isNewsEntryValid(BDDMockito.eq(request), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isContentValid(BDDMockito.eq(content), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(Arrays.asList()).when(validator)
        .isNewsImageValid(BDDMockito.eq(image), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(error).when(validator).computeError(BDDMockito.anyList());

    Error result = validator.validateCreate(request, Locale.FRANCE);

    Assert.assertNotNull(result);
    BDDMockito.verify(validator, BDDMockito.times(0)).isIdValid(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsEntryValid(BDDMockito.eq(request),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isContentValid(BDDMockito.eq(content),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(validator, BDDMockito.times(1)).isNewsImageValid(BDDMockito.eq(image),
        BDDMockito.eq(Locale.FRANCE));

  }

}
