package com.cmpl.web.core.factory.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuDTOBuilder;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;

;

@RunWith(MockitoJUnitRunner.class)
public class MenuFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private MenuFactoryImpl menuFactory;

  @Mock
  private MenuService menuService;

  @Mock
  private BackMenu backMenu;

  @Test
  public void testComputeBackMenuItems() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label).title(title).build();

    BDDMockito.given(backMenu.getItems()).willReturn(Arrays.asList(item));
    BDDMockito
        .doReturn(index)
        .when(menuFactory)
        .computeMenuItem(BDDMockito.any(BACK_PAGE.class), BDDMockito.any(BackMenuItem.class),
            BDDMockito.eq(Locale.FRANCE), BDDMockito.anyList(), BDDMockito.anyList());

    List<MenuItem> result = menuFactory.computeBackMenuItems(BACK_PAGE.NEWS_CREATE, Locale.FRANCE);
    Assert.assertTrue(index == result.get(0));

  }

  @Test
  public void testComputeMenuItemBACK_MENULocale() throws Exception {

    String href = "/";
    String label = "label";
    String title = "title";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label).title(BACK_PAGE.NEWS_CREATE.getTitle())
        .build();

    BDDMockito.doReturn(href).when(menuFactory)
        .getI18nValue(BDDMockito.eq(item.getHref()), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(label).when(menuFactory)
        .getI18nValue(BDDMockito.eq(item.getLabel()), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(title).when(menuFactory)
        .getI18nValue(BDDMockito.eq(item.getTitle()), BDDMockito.eq(Locale.FRANCE));

    MenuItem result = menuFactory.computeMenuItem(BACK_PAGE.NEWS_CREATE, item, Locale.FRANCE);

    Assert.assertEquals(href, result.getHref());
    Assert.assertEquals(label, result.getLabel());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertEquals("active", result.getCustomCssClass());

  }

  @Test
  public void testComputeCustomCssClass_BACK_PAGE_active() {

    String href = "/";
    String label = "label";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label).title(BACK_PAGE.NEWS_CREATE.getTitle())
        .build();

    String result = menuFactory.computeCustomCssClass(BACK_PAGE.NEWS_CREATE, item);
    Assert.assertEquals("active", result);
  }

  @Test
  public void testComputeCustomCssClass_BACK_PAGE_empty() {

    String href = "/";
    String label = "label";

    BackMenuItem item = BackMenuItemBuilder.create().href(href).label(label).title(BACK_PAGE.NEWS_CREATE.getTitle())
        .build();

    String result = menuFactory.computeCustomCssClass(BACK_PAGE.LOGIN, item);
    Assert.assertTrue(!StringUtils.hasText(result));
  }

  @Test
  public void testComputeCustomCssClassPageDTOMenuDTO_Menu_Linked_To_Page() throws Exception {
    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    MenuDTO menu = MenuDTOBuilder.create().pageId("123456789").build();

    Assert.assertEquals("active", menuFactory.computeCustomCssClass(page, menu));
  }

  @Test
  public void testComputeCustomCssClassPageDTOMenuDTO_Menu_Not_Linked_To_Page() throws Exception {
    PageDTO page = new PageDTO();
    page.setId(123456789l);

    MenuDTO menu = MenuDTOBuilder.create().pageId("12345678").build();

    Assert.assertEquals("", menuFactory.computeCustomCssClass(page, menu));
  }

  @Test
  public void testComputeMenuItemPageDTOMenuDTO() throws Exception {
    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    MenuDTO menu = MenuDTOBuilder.create().pageId("12345678").href("someHref").label("someLabel").title("someTitle")
        .build();

    BDDMockito.doReturn(new ArrayList<>()).when(menuFactory)
        .computeSubMenuItems(BDDMockito.any(PageDTO.class), BDDMockito.any(MenuDTO.class));
    BDDMockito.doReturn("active").when(menuFactory)
        .computeCustomCssClass(BDDMockito.any(PageDTO.class), BDDMockito.any(MenuDTO.class));

    MenuItem result = menuFactory.computeMenuItem(page, menu);

    Assert.assertEquals(menu.getHref(), result.getHref());
    Assert.assertEquals(menu.getTitle(), result.getTitle());
    Assert.assertEquals(menu.getLabel(), result.getLabel());
    Assert.assertEquals("active", result.getCustomCssClass());
  }

  @Test
  public void testComputeSubMenuItems() throws Exception {

    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    MenuDTO child = MenuDTOBuilder.create().parentId("12345678").href("someChildHref").label("someChildLabel")
        .title("someChildTitle").build();
    List<MenuDTO> children = new ArrayList<>();
    children.add(child);
    MenuDTO menu = MenuDTOBuilder.create().pageId("12345678").href("someHref").label("someLabel").title("someTitle")
        .children(children).build();

    MenuItem menuItem = MenuItemBuilder.create().href("someChildHref").label("someChildLabel").title("someChildTitle")
        .customCssClass("").build();

    BDDMockito.doReturn(menuItem).when(menuFactory)
        .computeMenuItem(BDDMockito.any(PageDTO.class), BDDMockito.any(MenuDTO.class));

    List<MenuItem> result = menuFactory.computeSubMenuItems(page, menu);

    Assert.assertTrue(result.size() == 1);
    MenuItem item = result.get(0);
    Assert.assertEquals(child.getHref(), item.getHref());
    Assert.assertEquals(child.getTitle(), item.getTitle());
    Assert.assertEquals(child.getLabel(), item.getLabel());
    Assert.assertEquals("", item.getCustomCssClass());

  }

  @Test
  public void testComputeMenuItems() throws Exception {

    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    MenuDTO menu = MenuDTOBuilder.create().pageId("12345678").href("someHref").label("someLabel").title("someTitle")
        .build();
    List<MenuDTO> menus = new ArrayList<>();
    menus.add(menu);

    BDDMockito.given(menuService.getMenus()).willReturn(menus);

    MenuItem menuItem = MenuItemBuilder.create().href("someChildHref").label("someChildLabel").title("someChildTitle")
        .customCssClass("").build();

    BDDMockito.doReturn(menuItem).when(menuFactory)
        .computeMenuItem(BDDMockito.any(PageDTO.class), BDDMockito.any(MenuDTO.class));

    List<MenuItem> menuItems = menuFactory.computeMenuItems(page, Locale.FRANCE);
    Assert.assertEquals(menuItem, menuItems.get(0));

  }
}
