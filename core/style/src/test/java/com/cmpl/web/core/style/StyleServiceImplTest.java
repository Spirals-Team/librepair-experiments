package com.cmpl.web.core.style;

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
  private StyleRepository styleRepository;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private StyleServiceImpl styleService;

  @Test
  public void testToEntity() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    StyleDTO dto = StyleDTOBuilder.create().media(media).content("someContent").build();

    BDDMockito.doNothing().when(styleService).fillObject(BDDMockito.any(StyleDTO.class), BDDMockito.any(Style.class));
    Style result = styleService.toEntity(dto);

    Assert.assertTrue(media.getId() == Long.parseLong(result.getMediaId()));

    BDDMockito.verify(styleService, BDDMockito.times(1)).fillObject(BDDMockito.any(StyleDTO.class),
        BDDMockito.any(Style.class));
  }

  @Test
  public void testToDTO_Media_Not_Null() throws Exception {
    Style style = StyleBuilder.create().mediaId("123456789").build();
    BDDMockito.doNothing().when(styleService).fillObject(BDDMockito.any(Style.class), BDDMockito.any(StyleDTO.class));

    MediaDTO media = new MediaDTO();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    BDDMockito.doReturn("someContent").when(styleService).readMediaContent(BDDMockito.any(MediaDTO.class));

    StyleDTO result = styleService.toDTO(style);
    Assert.assertEquals("someContent", result.getContent());
    Assert.assertEquals(media, result.getMedia());
  }

  @Test
  public void testToDTO_Media_Null() throws Exception {
    Style style = StyleBuilder.create().build();
    BDDMockito.doNothing().when(styleService).fillObject(BDDMockito.any(Style.class), BDDMockito.any(StyleDTO.class));

    StyleDTO result = styleService.toDTO(style);
    Assert.assertNull(result.getContent());
    Assert.assertNull(result.getMedia());

    BDDMockito.verify(styleService, BDDMockito.times(0)).readMediaContent(BDDMockito.any(MediaDTO.class));
  }

  @Test
  public void testCreateEntity() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().name("someName").id(123456789l).build();
    StyleDTO dto = StyleDTOBuilder.create().media(media).content("someContent").build();

    Style entity = StyleBuilder.create().build();

    BDDMockito.doReturn(entity).when(styleService).toEntity(BDDMockito.any(StyleDTO.class));
    BDDMockito.given(styleRepository.save(BDDMockito.any(Style.class))).willReturn(entity);
    BDDMockito.doReturn(dto).when(styleService).toDTO(BDDMockito.any(Style.class));

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

    BDDMockito.doReturn(entity).when(styleService).toEntity(BDDMockito.any(StyleDTO.class));
    BDDMockito.given(styleRepository.save(BDDMockito.any(Style.class))).willReturn(entity);
    BDDMockito.doReturn(dto).when(styleService).toDTO(BDDMockito.any(Style.class));

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

    BDDMockito.given(styleRepository.findAll()).willReturn(null);
    Assert.assertNull(styleService.getStyle());

  }

  @Test
  public void testGetStyle_Not_Null() throws Exception {
    List<Style> styles = new ArrayList<>();
    Style style = StyleBuilder.create().build();
    styles.add(style);

    BDDMockito.given(styleRepository.findAll()).willReturn(styles);

    StyleDTO styleDTO = new StyleDTO();
    BDDMockito.doReturn(styleDTO).when(styleService).toDTO(BDDMockito.any(Style.class));

    Assert.assertEquals(styleDTO, styleService.getStyle());
  }
}
