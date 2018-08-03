package com.cmpl.web.core.news.entry;

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

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class NewsEntryDispatcherImplTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Mock
  private NewsEntryTranslator translator;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private FileService fileService;
  @Mock
  private MediaService mediaService;

  @InjectMocks
  @Spy
  private NewsEntryDispatcherImpl dispatcher;

  @Test
  public void testDeleteEntity_Ok() throws Exception {

    dispatcher.deleteEntity(String.valueOf(1L), Locale.FRANCE);

    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).deleteEntity(BDDMockito.anyLong());
  }

  @Test
  public void testUpdateEntity_Ok() throws Exception {

    NewsEntryResponse response = new NewsEntryResponse();

    BDDMockito.given(translator.fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class))).willReturn(response);

    NewsEntryDTO dto = NewsEntryDTOBuilder.create().build();
    BDDMockito.given(translator.fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class))).willReturn(dto);
    BDDMockito.given(newsEntryService.updateEntity(BDDMockito.any(NewsEntryDTO.class))).willReturn(dto);

    NewsEntryResponse result = dispatcher.updateEntity(new NewsEntryRequest(), String.valueOf(1L), Locale.FRANCE);

    Assert.assertEquals(response, result);

    BDDMockito.verify(translator, BDDMockito.times(1)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).updateEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

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

    BDDMockito.verify(translator, BDDMockito.times(1)).fromRequestToDTO(BDDMockito.any(NewsEntryRequest.class));
    BDDMockito.verify(newsEntryService, BDDMockito.times(1)).createEntity(BDDMockito.any(NewsEntryDTO.class));
    BDDMockito.verify(translator, BDDMockito.times(1)).fromDTOToResponse(BDDMockito.any(NewsEntryDTO.class));

  }

}
