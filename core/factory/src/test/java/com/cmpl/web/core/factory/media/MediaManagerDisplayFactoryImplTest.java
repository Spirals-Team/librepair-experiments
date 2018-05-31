package com.cmpl.web.core.factory.media;

import java.util.ArrayList;
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
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class MediaManagerDisplayFactoryImplTest {

  @Mock
  private MediaService mediaService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;

  @Spy
  @InjectMocks
  private MediaManagerDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeModelAndViewForViewAllMedias() throws Exception {

    PageWrapper<MediaDTO> wrapper = new PageWrapper<>();
    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(wrapper).when(displayFactory)
        .computePageWrapper(BDDMockito.any(Locale.class), BDDMockito.anyInt());

    ModelAndView result = displayFactory.computeModelAndViewForViewAllMedias(Locale.FRANCE, 0);

    Assert.assertEquals(wrapper, result.getModel().get("wrappedMedias"));

  }

  @Test
  public void testComputeEntries_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MediaDTO> medias = new ArrayList<>();
    PageImpl<MediaDTO> page = new PageImpl<>(medias);
    BDDMockito.given(mediaService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MediaDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertTrue(CollectionUtils.isEmpty(result.getContent()));

  }

  @Test
  public void testComputeMediasEntries_Not_Empty() throws Exception {

    BDDMockito.given(contextHolder.getElementsPerPage()).willReturn(5);
    List<MediaDTO> medias = new ArrayList<>();
    MediaDTO media = MediaDTOBuilder.create().build();
    medias.add(media);
    PageImpl<MediaDTO> page = new PageImpl<>(medias);
    BDDMockito.given(mediaService.getPagedEntities(BDDMockito.any(PageRequest.class))).willReturn(page);

    Page<MediaDTO> result = displayFactory.computeEntries(Locale.FRANCE, 1);
    Assert.assertEquals(6, result.getTotalElements());

  }

  @Test
  public void testComputePageWrapperOfMedias() throws Exception {

    List<MediaDTO> medias = new ArrayList<>();
    MediaDTO media = MediaDTOBuilder.create().build();
    medias.add(media);
    PageImpl<MediaDTO> page = new PageImpl<>(medias);

    String pageLabel = "Page 1";

    BDDMockito.doReturn(page).when(displayFactory).computeEntries(BDDMockito.any(Locale.class), BDDMockito.anyInt());
    BDDMockito.doReturn(pageLabel).when(displayFactory)
        .getI18nValue(BDDMockito.anyString(), BDDMockito.any(Locale.class), BDDMockito.anyInt(), BDDMockito.anyInt());
    PageWrapper<MediaDTO> wrapper = displayFactory.computePageWrapper(Locale.FRANCE, 1);

    Assert.assertEquals(0, wrapper.getCurrentPageNumber());
    Assert.assertTrue(wrapper.isFirstPage());
    Assert.assertTrue(wrapper.isLastPage());
    Assert.assertEquals(page, wrapper.getPage());
    Assert.assertEquals(1, wrapper.getTotalPages());
    Assert.assertEquals("/manager/medias", wrapper.getPageBaseUrl());
    Assert.assertEquals(pageLabel, wrapper.getPageLabel());

  }

  @Test
  public void testComputeModelAndViewForUploadMedia() throws Exception {
    ModelAndView mediaManager = new ModelAndView();
    String test = "test";
    mediaManager.addObject("test", test);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(displayFactory).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    displayFactory.computeModelAndViewForUploadMedia(Locale.FRANCE);

  }
}
