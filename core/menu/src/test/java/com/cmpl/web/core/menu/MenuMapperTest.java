package com.cmpl.web.core.menu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.models.Menu;

@RunWith(MockitoJUnitRunner.class)
public class MenuMapperTest {

  @Spy
  private MenuMapper mapper;

  @Test
  public void testToDTO() throws Exception {

    Menu entity = MenuBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(Menu.class), BDDMockito.any(MenuDTO.class));
    mapper.toDTO(entity);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(Menu.class),
        BDDMockito.any(MenuDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    MenuDTO dto = MenuDTOBuilder.create().build();

    BDDMockito.doNothing().when(mapper).fillObject(BDDMockito.any(MenuDTO.class), BDDMockito.any(Menu.class));
    mapper.toEntity(dto);

    BDDMockito.verify(mapper, BDDMockito.times(1)).fillObject(BDDMockito.any(MenuDTO.class),
        BDDMockito.any(Menu.class));
  }

}
