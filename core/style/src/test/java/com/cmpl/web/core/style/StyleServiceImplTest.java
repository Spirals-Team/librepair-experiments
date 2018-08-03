package com.cmpl.web.core.style;

import com.cmpl.web.core.models.Style;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;

@RunWith(MockitoJUnitRunner.class)
public class StyleServiceImplTest {

  @Mock
  private StyleMapper mapper;

  @Mock
  private StyleDAO styleDAO;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private StyleServiceImpl styleService;

  @Test
  public void testCreateEntity() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().name("someName").id(123456789l).build();
    StyleDTO dto = StyleDTOBuilder.create().media(media).content("someContent").build();

    Style entity = StyleBuilder.create().build();

    BDDMockito.doReturn(entity).when(mapper).toEntity(BDDMockito.any(StyleDTO.class));
    BDDMockito.given(styleDAO.createEntity(BDDMockito.any(Style.class))).willReturn(entity);
    BDDMockito.doReturn(dto).when(mapper).toDTO(BDDMockito.any(Style.class));

    StyleDTO result = styleService.createEntity(dto);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(mediaService, BDDMockito.times(1)).createEntity(BDDMockito.any(MediaDTO.class));
    BDDMockito.verify(fileService, BDDMockito.times(1)).saveMediaOnSystem(BDDMockito.anyString(),
        BDDMockito.any(byte[].class));

  }

  @Test
  public void testUpdateEntity() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().name("someName").id(123456789l).build();
    StyleDTO dto = StyleDTOBuilder.create().media(media).content("someContent").build();

    Style entity = StyleBuilder.create().build();

    BDDMockito.doReturn(entity).when(mapper).toEntity(BDDMockito.any(StyleDTO.class));
    BDDMockito.given(styleDAO.updateEntity(BDDMockito.any(Style.class))).willReturn(entity);
    BDDMockito.doReturn(dto).when(mapper).toDTO(BDDMockito.any(Style.class));

    StyleDTO result = styleService.updateEntity(dto);

    Assert.assertEquals(dto, result);

    BDDMockito.verify(fileService, BDDMockito.times(1)).saveMediaOnSystem(BDDMockito.anyString(),
        BDDMockito.any(byte[].class));

  }

  @Test
  public void testGetStyle_Null() throws Exception {

    List<Style> styles = new ArrayList<>();
    Style style = StyleBuilder.create().build();
    styles.add(style);

    BDDMockito.given(styleDAO.getStyle()).willReturn(null);
    Assert.assertNull(styleService.getStyle());

  }

  @Test
  public void testGetStyle_Not_Null() throws Exception {
    Style style = StyleBuilder.create().build();

    BDDMockito.given(styleDAO.getStyle()).willReturn(style);

    StyleDTO styleDTO = new StyleDTO();
    BDDMockito.doReturn(styleDTO).when(mapper).toDTO(BDDMockito.any(Style.class));

    Assert.assertEquals(styleDTO, styleService.getStyle());
  }
}
