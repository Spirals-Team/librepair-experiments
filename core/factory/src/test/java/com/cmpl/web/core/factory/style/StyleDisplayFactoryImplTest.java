package com.cmpl.web.core.factory.style;

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

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleDTOBuilder;
import com.cmpl.web.core.style.StyleForm;
import com.cmpl.web.core.style.StyleService;

@RunWith(MockitoJUnitRunner.class)
public class StyleDisplayFactoryImplTest {

  @Mock
  private StyleService styleService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private StyleDisplayFactoryImpl displayFactory;

  @Test
  public void testInitMedia() throws Exception {

    MediaDTO result = displayFactory.initMedia();

    Assert.assertEquals("styles.css", result.getName());
    Assert.assertEquals(".css", result.getExtension());
    Assert.assertTrue(0l == result.getSize());
    Assert.assertEquals("text/css", result.getContentType());
    Assert.assertEquals("/public/media/styles.css", result.getSrc());
  }

  @Test
  public void testInitStyle() throws Exception {

    MediaDTO media = MediaDTOBuilder.create().id(123456789l).build();
    BDDMockito.doReturn(media).when(displayFactory).initMedia();

    StyleDTO style = StyleDTOBuilder.create().media(media).build();
    BDDMockito.given(styleService.createEntity(BDDMockito.any(StyleDTO.class))).willReturn(style);

    Assert.assertEquals(style, displayFactory.initStyle());
  }

  @Test
  public void testComputeModelAndViewForViewStyles_Init_Styles() throws Exception {
    StyleDTO style = StyleDTOBuilder.create().build();

    BDDMockito.given(styleService.getStyle()).willReturn(null);
    BDDMockito.doReturn(style).when(displayFactory).initStyle();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    ModelAndView result = displayFactory.computeModelAndViewForViewStyles(Locale.FRANCE);
    Assert.assertEquals(style, result.getModel().get("style"));
  }

  @Test
  public void testComputeModelAndViewForViewStyles_No_Init_Styles() throws Exception {
    StyleDTO style = StyleDTOBuilder.create().build();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.given(styleService.getStyle()).willReturn(style);

    ModelAndView result = displayFactory.computeModelAndViewForViewStyles(Locale.FRANCE);
    Assert.assertEquals(style, result.getModel().get("style"));
  }

  @Test
  public void testComputeModelAndViewForUpdateStyles_Init_Styles() throws Exception {
    StyleDTO style = StyleDTOBuilder.create().media(MediaDTOBuilder.create().build()).content("someContent").build();
    StyleForm form = new StyleForm(style);

    BDDMockito.given(styleService.getStyle()).willReturn(null);
    BDDMockito.doReturn(style).when(displayFactory).initStyle();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    ModelAndView result = displayFactory.computeModelAndViewForUpdateStyles(Locale.FRANCE);
    Assert.assertEquals(form.getContent(), ((StyleForm) result.getModel().get("styleForm")).getContent());
  }

  @Test
  public void testComputeModelAndViewForUpdateStyles_No_Init_Styles() throws Exception {
    StyleDTO style = StyleDTOBuilder.create().media(MediaDTOBuilder.create().build()).content("someContent").build();
    StyleForm form = new StyleForm(style);

    BDDMockito.given(styleService.getStyle()).willReturn(style);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForUpdateStyles(Locale.FRANCE);
    Assert.assertEquals(form.getContent(), ((StyleForm) result.getModel().get("styleForm")).getContent());
  }
}
