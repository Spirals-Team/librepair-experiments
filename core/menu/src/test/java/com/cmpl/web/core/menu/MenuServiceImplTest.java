package com.cmpl.web.core.menu;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {

  @Mock
  private MenuRepository menuRepository;

  @Spy
  @InjectMocks
  private MenuServiceImpl menuService;

  @Test
  public void testToDTO() throws Exception {

    Menu entity = MenuBuilder.create().build();

    BDDMockito.doNothing().when(menuService).fillObject(BDDMockito.any(Menu.class), BDDMockito.any(MenuDTO.class));
    menuService.toDTO(entity);

    BDDMockito.verify(menuService, BDDMockito.times(1)).fillObject(BDDMockito.any(Menu.class),
        BDDMockito.any(MenuDTO.class));
  }

  @Test
  public void testToEntity() throws Exception {
    MenuDTO dto = MenuDTOBuilder.create().build();

    BDDMockito.doNothing().when(menuService).fillObject(BDDMockito.any(MenuDTO.class), BDDMockito.any(Menu.class));
    menuService.toEntity(dto);

    BDDMockito.verify(menuService, BDDMockito.times(1)).fillObject(BDDMockito.any(MenuDTO.class),
        BDDMockito.any(Menu.class));
  }

  @Test
  public void testComputeMenuDTOToReturn() throws Exception {
    MenuDTO dto = MenuDTOBuilder.create().id(123456789l).build();

    BDDMockito.doReturn(dto).when(menuService).toDTO(BDDMockito.any(Menu.class));
    BDDMockito.doReturn(Arrays.asList(dto)).when(menuService).computeMenus(BDDMockito.anyList());

    Menu entity = MenuBuilder.create().build();
    BDDMockito.given(menuRepository.findByParentId(BDDMockito.anyString())).willReturn(Arrays.asList(entity));

    Assert.assertEquals(dto.getId(), menuService.computeMenuDTOToReturn(entity).getId());
  }

  @Test
  public void testComputeSubMenus() throws Exception {

    MenuDTO menuToAdd = MenuDTOBuilder.create().build();

    BDDMockito.doReturn(menuToAdd).when(menuService).computeMenuDTOToReturn(BDDMockito.any(Menu.class));

    Assert.assertEquals(menuToAdd, menuService.computeMenus(Arrays.asList(MenuBuilder.create().build())).get(0));
  }

  @Test
  public void testToListDTO() throws Exception {
    MenuDTO menuToAdd = MenuDTOBuilder.create().build();
    BDDMockito.doReturn(Arrays.asList(menuToAdd)).when(menuService).computeMenus(BDDMockito.anyList());

    Assert.assertEquals(menuToAdd, menuService.toListDTO(Arrays.asList(MenuBuilder.create().build())).get(0));
    BDDMockito.verify(menuService, BDDMockito.times(1)).computeMenus(BDDMockito.anyList());
  }

  @Test
  public void testGetMenus() throws Exception {
    MenuDTO menuDTOToFind = MenuDTOBuilder.create().build();

    Menu menuToFind = MenuBuilder.create().build();
    BDDMockito.given(menuRepository.findAll(BDDMockito.any(Sort.class))).willReturn(Arrays.asList(menuToFind));
    BDDMockito.doReturn(Arrays.asList(menuDTOToFind)).when(menuService).toListDTO(BDDMockito.anyList());

    Assert.assertEquals(menuDTOToFind, menuService.getMenus().get(0));
  }
}
