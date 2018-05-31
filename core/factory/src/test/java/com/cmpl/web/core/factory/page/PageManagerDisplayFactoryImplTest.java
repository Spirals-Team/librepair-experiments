package com.cmpl.web.core.factory.page;

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
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageCreateForm;
import com.cmpl.web.core.page.PageCreateFormBuilder;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.page.PageUpdateForm;
import com.cmpl.web.core.page.PageUpdateFormBuilder;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@RunWith(MockitoJUnitRunner.class)
public class PageManagerDisplayFactoryImplTest {

  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private WidgetPageService widgetPageService;
  @Mock
  private WidgetService widgetService;

  @Spy
  @InjectMocks
  private PageManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeModelAndViewForUpdatePageFooter() throws Exception {

    PageDTO dto = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(dto);

    PageUpdateForm form = PageUpdateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory)
        .createUpdateForm(BDDMockito.any(PageDTO.class), BDDMockito.anyString());

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageFooter(Locale.FRANCE, "123456789",
        Locale.FRANCE.getLanguage());

    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePageHeader() throws Exception {

    PageDTO dto = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(dto);

    PageUpdateForm form = PageUpdateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory)
        .createUpdateForm(BDDMockito.any(PageDTO.class), BDDMockito.anyString());

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageHeader(Locale.FRANCE, "123456789",
        Locale.FRANCE.getLanguage());
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePageBody() throws Exception {

    PageDTO dto = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(dto);

    PageUpdateForm form = PageUpdateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory)
        .createUpdateForm(BDDMockito.any(PageDTO.class), BDDMockito.anyString());

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageBody(Locale.FRANCE, "123456789",
        Locale.FRANCE.getLanguage());
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePageMain() throws Exception {

    PageDTO dto = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(dto);

    PageUpdateForm form = PageUpdateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory)
        .createUpdateForm(BDDMockito.any(PageDTO.class), BDDMockito.anyString());

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePageMain(Locale.FRANCE, "123456789",
        Locale.FRANCE.getLanguage());
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForUpdatePage() throws Exception {

    PageDTO dto = PageDTOBuilder.create().build();
    BDDMockito.given(pageService.getEntity(BDDMockito.anyLong(), BDDMockito.anyString())).willReturn(dto);

    PageUpdateForm form = PageUpdateFormBuilder.create().build();

    BreadCrumbItem item = BreadCrumbItemBuilder.create().text("someText").build();
    BreadCrumb breadcrumb = BreadCrumbBuilder.create().items(Arrays.asList(item)).build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(form).when(displayFactory)
        .createUpdateForm(BDDMockito.any(PageDTO.class), BDDMockito.anyString());

    ModelAndView result = displayFactory.computeModelAndViewForUpdatePage(Locale.FRANCE, "123456789",
        Locale.FRANCE.getLanguage());
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }

  @Test
  public void testComputeModelAndViewForCreatePage() throws Exception {

    PageCreateForm form = PageCreateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory).computeCreateForm();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    ModelAndView result = displayFactory.computeModelAndViewForCreatePage(Locale.FRANCE);
    Assert.assertNotNull(result.getModel().get("createForm"));
  }

  @Test
  public void testCreateUpdateForm() throws Exception {
    PageDTO dto = PageDTOBuilder.create().body("someBody").footer("someFooter").header("someHeader").build();

    PageUpdateForm result = displayFactory.createUpdateForm(dto, "fr");
    Assert.assertEquals(dto.getBody(), result.getBody());
    Assert.assertEquals(dto.getHeader(), result.getHeader());
    Assert.assertEquals(dto.getFooter(), result.getFooter());
  }

  @Test
  public void testComputeModelAndViewForViewAllPages() throws Exception {

    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = PageDTOBuilder.create().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    boolean isFirstPage = true;
    boolean isLastPage = true;
    int totalPages = 1;
    int currentPageNumber = 1;
    PageWrapper<PageDTO> wrapper = new PageWrapperBuilder<PageDTO>().currentPageNumber(currentPageNumber)
        .firstPage(isFirstPage).lastPage(isLastPage).page(page).totalPages(totalPages).pageBaseUrl("/manager/pages")
        .pageLabel("someLabel").build();

    BDDMockito.doReturn(wrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForViewAllPages(Locale.FRANCE, 0);
    Assert.assertNotNull(result.getModel().get("wrappedPages"));

  }

  @Test
  public void testComputeEntries_Not_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = PageDTOBuilder.create().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    BDDMockito.given(pageService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<PageDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());
  }

  @Test
  public void testComputeEntries_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<PageDTO> pages = new ArrayList<>();
    PageImpl<PageDTO> page = new PageImpl<>(pages);
    BDDMockito.given(pageService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<PageDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));
  }

  @Test
  public void testComputePageWrapperOfPages() throws Exception {

    List<PageDTO> pages = new ArrayList<>();
    PageDTO pageDTO = PageDTOBuilder.create().build();
    pages.add(pageDTO);
    PageImpl<PageDTO> page = new PageImpl<>(pages);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<PageDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/pages", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());
  }
}
