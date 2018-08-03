package com.cmpl.web.core.widget;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Widget;

@RunWith(MockitoJUnitRunner.class)
public class WidgetServiceImplTest {

  @Mock
  private WidgetMapper mapper;

  @Mock
  private FileService fileService;

  @Mock
  private WidgetDAO widgetDAO;

  @InjectMocks
  @Spy
  private WidgetServiceImpl widgetService;

  @Test
  public void testFindByName_Found() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();

    Widget widget = WidgetBuilder.create().build();

    BDDMockito.doReturn(result).when(mapper).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(widgetDAO.findByName(BDDMockito.anyString())).willReturn(widget);
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn("someContent");

    WidgetDTO widgetDTO = widgetService.findByName("someName", Locale.FRANCE.getLanguage());

    Assert.assertEquals(result, widgetDTO);
    Assert.assertEquals("someContent", widgetDTO.getPersonalization());
  }

  @Test
  public void testFindByName_Not_Found() {

    BDDMockito.given(widgetDAO.findByName(BDDMockito.anyString())).willReturn(null);

    WidgetDTO result = widgetService.findByName("someName", Locale.FRANCE.getLanguage());
    Assert.assertNull(result.getId());

  }

  @Test
  public void testGetEntity_No_Personalization() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();
    Widget widget = WidgetBuilder.create().build();

    Widget optional = WidgetBuilder.create().build();

    BDDMockito.given(widgetDAO.getEntity(BDDMockito.anyLong())).willReturn(optional);
    BDDMockito.doReturn(result).when(mapper).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn(null);

    WidgetDTO resultDTO = widgetService.getEntity(123456789L, Locale.FRANCE.getLanguage());

    Assert.assertEquals(result, resultDTO);
    Assert.assertNull(resultDTO.getPersonalization());

  }

  @Test
  public void testGetEntity_With_Personalization() {
    WidgetDTO result = WidgetDTOBuilder.create().id(123456789l).build();

    Widget optional = WidgetBuilder.create().build();

    BDDMockito.given(widgetDAO.getEntity(BDDMockito.anyLong())).willReturn(optional);
    BDDMockito.doReturn(result).when(mapper).toDTO(BDDMockito.any(Widget.class));
    BDDMockito.given(fileService.readFileContentFromSystem(BDDMockito.anyString())).willReturn("someContent");

    WidgetDTO resultDTO = widgetService.getEntity(123456789L, Locale.FRANCE.getLanguage());

    Assert.assertEquals(result.getId(), resultDTO.getId());
    Assert.assertEquals("someContent", resultDTO.getPersonalization());
  }

  @Test
  public void testUpdateEntity() {
    WidgetDTO toUpdate = WidgetDTOBuilder.create().personalization("someContent").build();

    Widget toSave = WidgetBuilder.create().build();

    BDDMockito.given(widgetDAO.updateEntity(BDDMockito.any())).willReturn(toSave);
    BDDMockito.doReturn(toUpdate).when(mapper).toDTO(BDDMockito.any(Widget.class));

    WidgetDTO result = widgetService.updateEntity(toUpdate, Locale.FRANCE.getLanguage());

    Assert.assertEquals(toUpdate, result);

    BDDMockito.verify(fileService, BDDMockito.times(1)).saveFileOnSystem(BDDMockito.anyString(),
        BDDMockito.anyString());
  }

  @Test
  public void testCreateEntity() {
    WidgetDTO toCreate = WidgetDTOBuilder.create().build();

    Widget toSave = WidgetBuilder.create().build();

    BDDMockito.given(widgetDAO.createEntity(BDDMockito.any())).willReturn(toSave);
    BDDMockito.given(mapper.toDTO(BDDMockito.any(Widget.class))).willReturn(toCreate);
    BDDMockito.given(mapper.toEntity(BDDMockito.any(WidgetDTO.class))).willReturn(toSave);

    WidgetDTO result = widgetService.createEntity(toCreate, Locale.FRANCE.getLanguage());

    Assert.assertEquals(toCreate, result);

    BDDMockito.verify(fileService, BDDMockito.times(1)).saveFileOnSystem(BDDMockito.anyString(),
        BDDMockito.nullable(String.class));
  }

}
