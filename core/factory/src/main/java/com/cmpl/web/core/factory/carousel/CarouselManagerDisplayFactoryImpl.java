package com.cmpl.web.core.factory.carousel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.carousel.CarouselCreateForm;
import com.cmpl.web.core.carousel.CarouselCreateFormBuilder;
import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselItemCreateForm;
import com.cmpl.web.core.carousel.CarouselItemCreateFormBuilder;
import com.cmpl.web.core.carousel.CarouselItemDTO;
import com.cmpl.web.core.carousel.CarouselItemService;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.CarouselUpdateForm;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;

public class CarouselManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<CarouselDTO>
    implements CarouselManagerDisplayFactory {

  private final CarouselService carouselService;
  private final MediaService mediaService;
  private final CarouselItemService carouselItemService;
  private final ContextHolder contextHolder;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String PAGES = "pages";
  private static final String MEDIAS = "medias";
  private static final String ITEMS = "items";

  public CarouselManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      CarouselService carouselService, CarouselItemService carouselItemService, MediaService mediaService,
      ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.carouselItemService = carouselItemService;
    this.carouselService = carouselService;
    this.contextHolder = contextHolder;
    this.mediaService = mediaService;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllCarousels(Locale locale, int pageNumber) {
    ModelAndView carouselsManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_VIEW, locale);
    LOGGER.info("Construction des carousels pour la page {}", BACK_PAGE.CAROUSELS_VIEW.name());

    PageWrapper<CarouselDTO> pagedCarouselDTOWrapped = computePageWrapper(locale, pageNumber);

    carouselsManager.addObject("wrappedCarousels", pagedCarouselDTOWrapped);

    return carouselsManager;
  }

  @Override
  protected Page<CarouselDTO> computeEntries(Locale locale, int pageNumber) {
    List<CarouselDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Direction.ASC, "name"));
    Page<CarouselDTO> pagedCarouselDTOEntries = carouselService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedCarouselDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedCarouselDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedCarouselDTOEntries.getTotalElements());
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarousel(Locale locale, String carouselId) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_UPDATE, locale);
    return computeModelAndViewForCarouselUpdate(carouselManager, carouselId);
  }

  CarouselUpdateForm createUpdateForm(CarouselDTO carousel) {
    return new CarouselUpdateForm(carousel);
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselMain(String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_main");
    return computeModelAndViewForCarouselUpdate(carouselManager, carouselId);
  }

  ModelAndView computeModelAndViewForCarouselUpdate(ModelAndView initializedModelAndView, String carouselId) {
    ModelAndView carouselManager = initializedModelAndView;
    CarouselDTO carousel = carouselService.getEntity(Long.parseLong(carouselId));
    carouselManager.addObject(UPDATE_FORM, createUpdateForm(carousel));

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(carousel.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) carouselManager.getModel().get("breadcrumb");
    if (breadCrumb != null) {
      if (canAddBreadCrumbItem(breadCrumb, item)) {
        breadCrumb.getItems().add(item);
      }
    }
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateCarouselItems(String carouselId) {
    ModelAndView carouselManager = new ModelAndView("back/carousels/edit/tab_items");
    carouselManager.addObject(CREATE_FORM, computeItemCreateForm(carouselId));
    List<MediaDTO> medias = mediaService.getEntities();
    carouselManager.addObject(MEDIAS, medias);
    List<CarouselItemDTO> items = carouselItemService.getByCarouselId(carouselId);
    carouselManager.addObject(ITEMS, items);
    return carouselManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateCarousel(Locale locale) {
    ModelAndView carouselManager = super.computeModelAndViewForBackPage(BACK_PAGE.CAROUSELS_CREATE, locale);
    carouselManager.addObject(CREATE_FORM, computeCreateForm());
    return carouselManager;
  }

  CarouselCreateForm computeCreateForm() {
    return CarouselCreateFormBuilder.create().build();
  }

  CarouselItemCreateForm computeItemCreateForm(String carouselId) {
    return CarouselItemCreateFormBuilder.create().carouselId(carouselId).build();
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/carousels";
  }

  @Override
  protected String getItemLink() {
    return "/manager/carousels/";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:carousels:create";
  }

}
