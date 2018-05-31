package com.cmpl.web.core.factory.carousel;

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
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.carousel.CarouselCreateForm;
import com.cmpl.web.core.carousel.CarouselCreateFormBuilder;
import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselDTOBuilder;
import com.cmpl.web.core.carousel.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.CarouselItemCreateFormBuilder;
import com.cmpl.web.core.carousel.CarouselItemDTO;
import com.cmpl.web.core.carousel.CarouselItemDTOBuilder;
import com.cmpl.web.core.carousel.CarouselItemService;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.CarouselUpdateForm;
import com.cmpl.web.core.carousel.CarouselUpdateFormBuilder;
import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class CarouselManagerDisplayFactoryImplTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private CarouselService carouselService;
  @Mock
  private PageService pageService;
  @Mock
  private MediaService mediaService;
  @Mock
  private CarouselItemService carouselItemService;
  @Mock
  private ContextHolder contextHolder;

  @Spy
  @InjectMocks
  private CarouselManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeItemCreateForm() throws Exception {
    Assert.assertEquals("123456789", displayFactory.computeItemCreateForm("123456789").getCarouselId());
  }

  @Test
  public void testComputeCreateForm() throws Exception {
    Assert.assertTrue(!StringUtils.hasText(displayFactory.computeCreateForm().getName()));
  }

  @Test
  public void testComputeModelAndViewForCreateCarousel() throws Exception {

    CarouselCreateForm form = CarouselCreateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory).computeCreateForm();

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    ModelAndView result = displayFactory.computeModelAndViewForCreateCarousel(Locale.FRANCE);
    Assert.assertNotNull(result.getModel().get("createForm"));

  }

  @Test
  public void testComputeModelAndViewForUpdateCarouselItems() throws Exception {

    CarouselItemCreateForm form = CarouselItemCreateFormBuilder.create().build();
    BDDMockito.doReturn(form).when(displayFactory).computeItemCreateForm(BDDMockito.anyString());

    MediaDTO media = MediaDTOBuilder.create().build();
    BDDMockito.given(mediaService.getEntities()).willReturn(Arrays.asList(media));

    CarouselItemDTO item = CarouselItemDTOBuilder.create().build();
    BDDMockito.given(carouselItemService.getByCarouselId(BDDMockito.anyString())).willReturn(Arrays.asList(item));

    ModelAndView result = displayFactory.computeModelAndViewForUpdateCarouselItems("123456789");
    Assert.assertNotNull(result.getModel().get("createForm"));
    Assert.assertNotNull(result.getModel().get("medias"));
    Assert.assertNotNull(result.getModel().get("items"));
  }

  @Test
  public void testComputeModelAndViewForUpdateCarouselMain() throws Exception {
    ModelAndView toReturn = new ModelAndView();
    BDDMockito.doReturn(toReturn).when(displayFactory)
        .computeModelAndViewForCarouselUpdate(BDDMockito.any(ModelAndView.class), BDDMockito.anyString());
    Assert.assertEquals(toReturn, displayFactory.computeModelAndViewForUpdateCarouselMain("123456789"));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeModelAndViewForCarouselUpdate(
        BDDMockito.any(ModelAndView.class), BDDMockito.anyString());
  }

  @Test
  public void testCreateUpdateForm() throws Exception {
    CarouselDTO dto = CarouselDTOBuilder.create().id(123456789l).build();

    Assert.assertEquals(dto.getId(), displayFactory.createUpdateForm(dto).getId());
  }

  @Test
  public void testComputeModelAndViewForUpdateCarousel() throws Exception {
    ModelAndView toReturn = new ModelAndView();
    BDDMockito.doReturn(toReturn).when(displayFactory)
        .computeModelAndViewForCarouselUpdate(BDDMockito.any(ModelAndView.class), BDDMockito.anyString());
    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    Assert.assertEquals(toReturn, displayFactory.computeModelAndViewForUpdateCarousel(Locale.FRANCE, "123456789"));
    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeModelAndViewForCarouselUpdate(
        BDDMockito.any(ModelAndView.class), BDDMockito.anyString());
  }

  @Test
  public void testComputeEntries_Not_Empty() throws Exception {
    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<CarouselDTO> carousels = new ArrayList<>();
    CarouselDTO carousel = CarouselDTOBuilder.create().build();
    carousels.add(carousel);
    PageImpl<CarouselDTO> page = new PageImpl<>(carousels);
    BDDMockito.given(carouselService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<CarouselDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());
  }

  @Test
  public void testComputePagesEntries_Empty() throws Exception {
    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<CarouselDTO> carousels = new ArrayList<>();
    PageImpl<CarouselDTO> page = new PageImpl<>(carousels);
    BDDMockito.given(carouselService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<CarouselDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));
  }

  @Test
  public void testComputePageWrapperOfCarousels() throws Exception {
    List<CarouselDTO> carousels = new ArrayList<>();
    CarouselDTO carousel = CarouselDTOBuilder.create().build();
    carousels.add(carousel);
    PageImpl<CarouselDTO> page = new PageImpl<>(carousels);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<CarouselDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/carousels", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());
  }

  @Test
  public void testComputeModelAndViewForViewAllCarousels() throws Exception {

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = new PageWrapperBuilder<CarouselDTO>().build();
    BDDMockito.doReturn(pagedCarouselDTOWrapped).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    ModelAndView result = displayFactory.computeModelAndViewForViewAllCarousels(Locale.FRANCE, 1);

    Assert.assertNotNull(result.getModel().get("wrappedCarousels"));

  }

  @Test
  public void testComputeModelAndViewForCarouselUpdate() throws Exception {
    CarouselUpdateForm form = CarouselUpdateFormBuilder.create().id(123456789l).build();
    BDDMockito.doReturn(form).when(displayFactory).createUpdateForm(BDDMockito.any(CarouselDTO.class));

    CarouselDTO carousel = CarouselDTOBuilder.create().build();
    BDDMockito.given(carouselService.getEntity(BDDMockito.anyLong())).willReturn(carousel);

    ModelAndView initializedModelAndView = new ModelAndView();
    ModelAndView result = displayFactory.computeModelAndViewForCarouselUpdate(initializedModelAndView, "123456789");
    Assert.assertNotNull(result.getModel().get("updateForm"));
  }
}
