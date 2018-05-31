package com.cmpl.web.core.news;

import java.util.Arrays;
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

import com.cmpl.web.core.common.error.ERROR_CAUSE;
import com.cmpl.web.core.common.error.ERROR_TYPE;
import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.common.error.ErrorCause;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private NewsEntryRequestValidator validator;
  @Mock
  private NewsEntryTranslator translator;
  @Mock
  private NewsEntryService newsEntryService;

  @InjectMocks
  @Spy
  private NewsEntryDispatcherImpl dispatcher;

  @Test
  public void testDeleteEntity_Ok() throws Exception {

    BDDMockito.doReturn(null).when(validator).validateDelete(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));

    dispatcher.deleteEntity(String.valueOf(1L), Locale.FRANCE);

    BDDMockito.verify(validator, BDDMockito.times(1)).validateDelete(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testDeleteEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());
    error.setCauses(Arrays.asList(errorCause));

    BDDMockito.doReturn(error).when(validator).validateDelete(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));

    dispatcher.deleteEntity(String.valueOf(1L), Locale.FRANCE);

    BDDMockito.verify(validator, BDDMockito.times(1)).validateDelete(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(newsEntryService, BDDMockito.times(0)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testUpdateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(null).when(validator).validateUpdate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class))).willReturn(response);

    NewsEntryDTO dto = NewsEntryDTOBuilder.create().build();
    BDDMockito.given(translator.fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class))).willReturn(dto);
    BDDMockito.given(newsEntryService.updateEntity(BDDMockito.any(NewsEntryDTO.class))).willReturn(dto);

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), Locale.FRANCE);

    Assert.assertEquals(response, result);

    BDDMockito.verify(validator, BDDMockito.times(1)).validateUpdate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).updateEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testUpdateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());
    error.setCauses(Arrays.asList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator).validateUpdate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), Locale.FRANCE);

    Assert.assertEquals(response.getError(), result.getError());

    BDDMockito.verify(validator, BDDMockito.times(1)).validateUpdate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(0)).updateEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.doReturn(response).when(translator).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

    NewsEntryDTO dto = NewsEntryDTOBuilder.create().build();
    BDDMockito.given(translator.fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class))).willReturn(dto);
    BDDMockito.given(newsEntryService.createEntity(BDDMockito.any(NewsEntryDTO.class))).willReturn(dto);

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), Locale.FRANCE);

    Assert.assertEquals(response, result);

    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

  }

  @Test
  public void testCreateEntity_Ko() throws Exception {

    ErrorCause errorCause = new ErrorCause();
    errorCause.setCode(ERROR_CAUSE.EMPTY_NEWS_ID.toString());
    errorCause.setMessage("Empty id");

    Error error = new Error();
    error.setCode(ERROR_TYPE.INVALID_REQUEST.toString());
    error.setCauses(Arrays.asList(errorCause));

    NewsEntryResponse response = new NewsEntryResponse();
    response.setError(error);

    BDDMockito.doReturn(error).when(validator).validateCreate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.eq(Locale.FRANCE));

    NewsEntryResponse result = dispatcher.createEntity(new NewsEntryRequest(), Locale.FRANCE);

    Assert.assertEquals(response.getError(), result.getError());

    BDDMockito.verify(validator, BDDMockito.times(1)).validateCreate(BDDMockito.any(NewsEntryRequest.class),
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(0)).createEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(0)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

  }
}
