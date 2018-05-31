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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuDTOBuilder;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;
import com.cmpl.web.core.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class MenuManagerDisplayFactoryImplTest {

  @Mock
  private MenuService menuService;
  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private MenuManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeEntries_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MenuDTO> menus = new ArrayList<>();
    PageImpl<MenuDTO> page = new PageImpl<>(menus);
    BDDMockito.given(menuService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MenuDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));

  }

  @Test
  public void testComputeEntries_Not_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MenuDTO> menus = new ArrayList<>();
    MenuDTO menu = MenuDTOBuilder.create().build();
    menus.add(menu);
    PageImpl<MenuDTO> page = new PageImpl<>(menus);
    BDDMockito.given(menuService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MenuDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());

  }

  @Test
  public void testComputePageWrapperOfMenus() throws Exception {

    List<MenuDTO> menus = new ArrayList<>();
    MenuDTO menu = MenuDTOBuilder.create().build();
    menus.add(menu);
    PageImpl<MenuDTO> page = new PageImpl<>(menus);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<MenuDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/menus", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());

  }

  @Test
  public void testComputeModelAndViewForCreateMenu() throws Exception {

    MenuDTO menu = MenuDTOBuilder.create().build();
    BDDMockito.given(menuService.getMenus()).willReturn(Arrays.asList(menu));

    PageDTO page = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getPages()).willReturn(Arrays.asList(page));
    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForCreateMenu(Locale.FRANCE);
    Assert.assertNotNull(result.getModel().get("menusThatCanBeParents"));
    Assert.assertNotNull(result.getModel().get("pagesThatCanBeLinkedTo"));
    Assert.assertNotNull(result.getModel().get("createForm"));

  }

  @Test
  public void testComputeModelAndViewForViewAllMenus() throws Exception {

    PageWrapper<MenuDTO> wrapper = new PageWrapperBuilder<MenuDTO>().build();
    BDDMockito.doReturn(wrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForViewAllMenus(Locale.FRANCE, 0);
    Assert.assertNotNull(result.getModel().get("wrappedMenus"));

  }

  @Test
  public void testComputeModelAndViewForUpdateMenu() throws Exception {
    MenuDTO possibleParent = MenuDTOBuilder.create().id(123456789l).build();
    MenuDTO notPossibleParent = MenuDTOBuilder.create().id(12345678l).build();

    BDDMockito.given(menuService.getMenus()).willReturn(Arrays.asList(notPossibleParent, possibleParent));

    PageDTO page = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getPages()).willReturn(Arrays.asList(page));

    BDDMockito.given(menuService.getEntity(BDDMockito.anyLong())).willReturn(notPossibleParent);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().text("someText").build();
    BreadCrumb breadcrumb = BreadCrumbBuilder.create().items(Arrays.asList(item)).build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdateMenu(Locale.FRANCE, "123456789");
    Assert.assertNotNull(result.getModel().get("menusThatCanBeParents"));
    Assert.assertNotNull(result.getModel().get("pagesThatCanBeLinkedTo"));
    Assert.assertNotNull(result.getModel().get("updateForm"));

  }

}
