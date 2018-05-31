package com.cmpl.web.core.widget;

import java.util.Locale;
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

import com.cmpl.web.core.file.FileService;

@RunWith(MockitoJUnitRunner.class)
public class WidgetServiceImplTest {

  @Mock
  private FileService fileService;

  @Mock
  private WidgetRepository widgetRepository;

  @Mock
  private ApplicationEventPublisher publisher;

  @InjectMocks
  @Spy
  private WidgetServiceImpl widgetService;

  @Test
  public void testToEntity() {

    WidgetDTO dto = WidgetDTOBuilder.create().build();

    BDDMockito.doNothing().when(widgetService).fillObject(BDDMockito.any(WidgetDTO.class),
        BDDMockito.any(Widget.class));
    widgetService.toEntity(dto);

    BDDMockito.verify(widgetService, BDDMockito.times(1)).fillObject(BDDMockito.any(WidgetDTO.class),
        BDDMockito.any(Widget.class));
  }

  @Test
  public void testToDTO() {
    Widget entity = WidgetBuilder.create().build();

    BDDMockito.doNothing().when(widgetService).fillObject(BDDMockito.any(Widget.class),
        BDDMockito.any(WidgetDTO.class));
    widgetService.toDTO(entity);

    BDDMockito.verify(widgetService, BDDMockito.times(1)).fillObject(BDDMockito.any(Widget.class),
        BDDMockito.any(WidgetDTO.class));
  }

  @Test
  public void testFindByName_Found() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();

    Widget widget = WidgetBuilder.create().build();

    BDDMockito.doReturn(result).when(widgetService).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(widgetRepository.findByName(BDDMockito.anyString())).willReturn(widget);
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn("someContent");

    WidgetDTO widgetDTO = widgetService.findByName("someName", Locale.FRANCE.getLanguage());

    Assert.assertEquals(result, widgetDTO);
    Assert.assertEquals("someContent", widgetDTO.getPersonalization());
  }

  @Test
  public void testFindByName_Not_Found() {

    BDDMockito.given(widgetRepository.findByName(BDDMockito.anyString())).willReturn(null);

    WidgetDTO result = widgetService.findByName("someName", Locale.FRANCE.getLanguage());
    Assert.assertNull(result.getId());

  }

  @Test
  public void testGetEntity_No_Personalization() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();
    Widget widget = WidgetBuilder.create().build();

    Optional<Widget> optional = Optional.of(WidgetBuilder.create().build());

    BDDMockito.given(widgetRepository.findById(BDDMockito.anyLong())).willReturn(optional);
    BDDMockito.doReturn(result).when(widgetService).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn(null);

    WidgetDTO resultDTO = widgetService.getEntity(123456789L, Locale.FRANCE.getLanguage());

    Assert.assertEquals(result, resultDTO);
    Assert.assertNull(resultDTO.getPersonalization());

  }

  @Test
  public void testGetEntity_With_Personalization() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();

    Optional<Widget> optional = Optional.of(WidgetBuilder.create().build());

    BDDMockito.given(widgetRepository.findById(BDDMockito.anyLong())).willReturn(optional);
    BDDMockito.doReturn(result).when(widgetService).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn("someContent");

    WidgetDTO resultDTO = widgetService.getEntity(123456789L, Locale.FRANCE.getLanguage());

    Assert.assertEquals(result.getId(), resultDTO.getId());
    Assert.assertEquals("someContent", resultDTO.getPersonalization());
  }

  @Test
  public void testUpdateEntity() {
    WidgetDTO toUpdate = WidgetDTOBuilder.create().personalization("someContent").build();

    Widget toSave = WidgetBuilder.create().build();

    BDDMockito.given(widgetRepository.save(BDDMockito.any())).willReturn(toSave);
    BDDMockito.doReturn(toUpdate).when(widgetService).toDTO(BDDMockito.any(Widget.class));

    WidgetDTO result = widgetService.updateEntity(toUpdate, Locale.FRANCE.getLanguage());

    Assert.assertEquals(toUpdate, result);

    BDDMockito.verify(fileService, BDDMockito.times(1)).saveFileOnSystem(BDDMockito.anyString(),
        BDDMockito.anyString());
  }

  @Test
  public void testCreateEntity() {
    WidgetDTO toCreate = WidgetDTOBuilder.create().build();

    Widget toSave = WidgetBuilder.create().build();

    BDDMockito.given(widgetRepository.save(BDDMockito.any())).willReturn(toSave);
    BDDMockito.doReturn(toCreate).when(widgetService).toDTO(BDDMockito.any(Widget.class));

    WidgetDTO result = widgetService.updateEntity(toCreate, Locale.FRANCE.getLanguage());

    Assert.assertEquals(toCreate, result);

    BDDMockito.verify(fileService, BDDMockito.times(1)).saveFileOnSystem(BDDMockito.anyString(),
        BDDMockito.nullable(String.class));
  }

}
