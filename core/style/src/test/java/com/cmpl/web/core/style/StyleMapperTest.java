package com.cmpl.web.core.style;

import com.cmpl.web.core.models.Style;
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
public class StyleMapperTest {

  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;

  @Spy
  @InjectMocks
  private StyleMapper mapper;

  @Test
  public void testToEntity() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    StyleDTO dto = StyleDTOBuilder.create().media(media).content("someContent").build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(StyleDTO.class), BDDMockito.any(Style.class));
    Style result = mapper.toEntity(dto);

    Assert.assertTrue(media.getId() == Long.parseLong(result.getMediaId()));

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(StyleDTO.class),
        BDDMockito.any(Style.class));
  }

  @Test
  public void testToDTO_Media_Not_Null() throws Exception {
    Style style = StyleBuilder.create().mediaId("123456789").build();
    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Style.class), BDDMockito.any(StyleDTO.class));

    MediaDTO media = new MediaDTO();
    BDDMockito.given(mediaService.getEntity(BDDMockito.anyLong())).willReturn(media);

    BDDMockito.doReturn("someContent").when(mapper).readMediaContent(BDDMockito.any(MediaDTO.class));

    StyleDTO result = mapper.toDTO(style);
    Assert.assertEquals("someContent", result.getContent());
    Assert.assertEquals(media, result.getMedia());
  }

  @Test
  public void testToDTO_Media_Null() throws Exception {
    Style style = StyleBuilder.create().build();
    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Style.class), BDDMockito.any(StyleDTO.class));

    StyleDTO result = mapper.toDTO(style);
    Assert.assertNull(result.getContent());
    Assert.assertNull(result.getMedia());

    BDDMockito.verify(mapper, BDDMockito.times(0)).readMediaContent(BDDMockito.any(MediaDTO.class));
  }
}
