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

import com.cmpl.web.core.models.Menu;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceImplTest {

  @Mock
  private MenuMapper mapper;

  @Mock
  private MenuDAO menuDAO;

  @Spy
  @InjectMocks
  private MenuServiceImpl menuService;

  @Test
  public void testComputeMenuDTOToReturn() throws Exception {
    MenuDTO dto = MenuDTOBuilder.create().id(123456789l).build();

    BDDMockito.doReturn(dto).when(mapper).toDTO(BDDMockito.any(Menu.class));
    BDDMockito.doReturn(Arrays.asList(dto)).when(menuService).computeMenus(BDDMockito.anyList());

    Menu entity = MenuBuilder.create().build();
    BDDMockito.given(menuDAO.findByParentId(BDDMockito.anyString())).willReturn(Arrays.asList(entity));

    Assert.assertEquals(dto.getId(), menuService.computeMenuDTOToReturn(entity).getId());
  }

  @Test
  public void testComputeSubMenus() throws Exception {

    MenuDTO menuToAdd = MenuDTOBuilder.create().build();

    BDDMockito.doReturn(menuToAdd).when(menuService).computeMenuDTOToReturn(BDDMockito.any(Menu.class));

    Assert.assertEquals(menuToAdd, menuService.computeMenus(Arrays.asList(MenuBuilder.create().build())).get(0));
  }

  @Test
  public void testGetMenus() throws Exception {
    MenuDTO menuDTOToFind = MenuDTOBuilder.create().build();

    Menu menuToFind = MenuBuilder.create().build();
    BDDMockito.given(mapper.toDTO(BDDMockito.any(Menu.class))).willReturn(menuDTOToFind);
    BDDMockito.given(menuDAO.getMenus(BDDMockito.any(Sort.class))).willReturn(Arrays.asList(menuToFind));

    Assert.assertEquals(menuDTOToFind, menuService.getMenus().get(0));
  }
}
