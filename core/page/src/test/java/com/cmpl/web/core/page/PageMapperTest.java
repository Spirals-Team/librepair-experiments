package com.cmpl.web.core.page;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.Page;

@RunWith(MockitoJUnitRunner.class)
public class PageMapperTest {

  @Spy
  private PageMapper mapper;

  @Test
  public void testToDTO() throws Exception {
    Page entity = PageBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Page.class), BDDMockito.any(PageDTO.class));
    mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(Page.class),
        BDDMockito.any(PageDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    PageDTO dto = PageDTOBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(PageDTO.class), BDDMockito.any(Page.class));
    mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Page.class));
  }

  @Test
  public void testToListDTO() throws Exception {
    PageDTO result = PageDTOBuilder.create().id(123456789l).build();
    BDDMockito.doReturn(result).when(mapper).toDTO(BDDMockito.any(Page.class));

    Page page = PageBuilder.create().build();
    Assert.assertEquals(result, mapper.toListDTO(Arrays.asList(page)).get(0));

  }

}
