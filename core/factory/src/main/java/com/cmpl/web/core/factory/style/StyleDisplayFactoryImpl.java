package com.cmpl.web.core.factory.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.StyleCreateFormBuilder;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.style.StyleUpdateForm;
import com.cmpl.web.core.style.StyleUpdateFormBuilder;

public class StyleDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<StyleDTO> implements StyleDisplayFactory {

  private final StyleService styleService;
  private final ContextHolder contextHolder;

  public StyleDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource, StyleService styleService,
      ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService, membershipService);
    this.styleService = Objects.requireNonNull(styleService);

    this.contextHolder = Objects.requireNonNull(contextHolder);

  }

  @Override
  public ModelAndView computeModelAndViewForViewAllStyles(Locale locale, int pageNumber) {

    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_VIEW, locale);
    LOGGER.info("Construction des styles pour la page {} ", BACK_PAGE.STYLES_VIEW.name());

    PageWrapper<StyleDTO> pagedStyleDTOWrapped = computePageWrapper(locale, pageNumber);

    stylesManager.addObject("wrappedStyles", pagedStyleDTOWrapped);

    return stylesManager;

  }

  @Override
  public ModelAndView computeModelAndViewForCreateStyle(Locale locale) {
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des styles");

    stylesManager.addObject("createForm", StyleCreateFormBuilder.create().content("").build());

    return stylesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyle(Locale locale, String styleId) {
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_UPDATE, locale);
    LOGGER.info("Construction du style pour la page {} ", BACK_PAGE.STYLES_UPDATE.name());
    StyleDTO style = styleService.getEntity(Long.parseLong(styleId));

    StyleUpdateForm updateForm = StyleUpdateFormBuilder.create().content(style.getContent())
        .creationDate(style.getCreationDate()).creationUser(style.getCreationUser()).id(style.getId())
        .modificationDate(style.getModificationDate()).modificationUser(style.getModificationUser())
        .mediaId(style.getMedia().getId()).mediaName(style.getMedia().getName()).build();

    stylesManager.addObject("updateForm", updateForm);

    return stylesManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyleMain(Locale locale, String styleId) {
    ModelAndView stylesManager = new ModelAndView("back/styles/edit/tab_main");

    LOGGER.info("Construction du style pour la page {} ", BACK_PAGE.STYLES_UPDATE.name());
    StyleDTO style = styleService.getEntity(Long.parseLong(styleId));

    StyleUpdateForm updateForm = StyleUpdateFormBuilder.create().content(style.getContent())
        .creationDate(style.getCreationDate()).creationUser(style.getCreationUser()).id(style.getId())
        .modificationDate(style.getModificationDate()).modificationUser(style.getModificationUser())
        .mediaId(style.getMedia().getId()).mediaName(style.getMedia().getName()).build();

    stylesManager.addObject("updateForm", updateForm);
    return stylesManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/styles";
  }

  @Override
  protected String getItemLink() {
    return "/manager/styles/";
  }

  @Override
  protected Page<StyleDTO> computeEntries(Locale locale, int pageNumber) {
    List<StyleDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Direction.ASC, "name"));
    Page<StyleDTO> pagedStyleDTOEntries = styleService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedStyleDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedStyleDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedStyleDTOEntries.getTotalElements());
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:style:create";
  }

}
