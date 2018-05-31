package com.cmpl.web.core.factory;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageDTOBuilder;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@RunWith(MockitoJUnitRunner.class)
public class DisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private PageService pageService;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private WidgetPageService widgetPageService;
  @Mock
  private WidgetService widgetService;

  @Spy
  @InjectMocks
  private DisplayFactoryImpl displayFactory;

  @Test
  public void testComputePageFooter() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_footer_fr", displayFactory.computePageFooter(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageHeader() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_header_fr", displayFactory.computePageHeader(page, Locale.FRANCE));
  }

  @Test
  public void testComputePageContent() throws Exception {

    PageDTO page = PageDTOBuilder.create().build();
    page.setName("test");

    Assert.assertEquals("test_fr", displayFactory.computePageContent(page, Locale.FRANCE));
  }

  @Test
  public void testComputeModelAndViewForPage_Without_News() throws Exception {
    PageDTO page = PageDTOBuilder.create().id(123456789l).build();

    BDDMockito.given(pageService.getPageByName(BDDMockito.anyString(), BDDMockito.anyString())).willReturn(page);

    BDDMockito.doReturn("test_footer_fr").when(displayFactory).computePageFooter(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Locale.class));
    BDDMockito.doReturn("test_header_fr").when(displayFactory).computePageHeader(BDDMockito.any(PageDTO.class),
        BDDMockito.any(Locale.class));
    BDDMockito.doReturn("someLink").when(displayFactory).computeHiddenLink(BDDMockito.any(Locale.class));

    ModelAndView result = displayFactory.computeModelAndViewForPage("somePage", Locale.FRANCE, 0);

    Assert.assertEquals("test_footer_fr", result.getModel().get("footerTemplate"));
    Assert.assertEquals("test_header_fr", result.getModel().get("header"));
    Assert.assertEquals("someLink", result.getModel().get("hiddenLink"));
    Assert.assertNull(result.getModel().get("wrappedNews"));
    Assert.assertNull(result.getModel().get("emptyMessage"));

  }

}
