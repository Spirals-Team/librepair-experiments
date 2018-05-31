package com.cmpl.web.core.factory.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetCreateForm;
import com.cmpl.web.core.widget.WidgetCreateFormBuilder;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.WidgetUpdateForm;
import com.cmpl.web.core.widget.WidgetUpdateFormBuilder;

public class WidgetManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<WidgetDTO>
    implements WidgetManagerDisplayFactory {

  private final WidgetService widgetService;
  private final ContextHolder contextHolder;
  private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  private static final String CREATE_FORM = "createForm";
  private static final String UPDATE_FORM = "updateForm";
  private static final String LINKABLE_ENTITIES = "linkableEntities";
  private static final String WIDGET_TYPES = "widgetTypes";
  private static final String LOCALES = "locales";
  private static final String TOOLTIP_KEY = "tooltipKey";
  private static final String MACROS_KEY = "macros";

  public WidgetManagerDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      ContextHolder contextHolder, WidgetService widgetService,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      PluginRegistry<WidgetProviderPlugin, String> widgetProviders, Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.widgetService = widgetService;
    this.contextHolder = contextHolder;
    this.widgetProviders = widgetProviders;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllWidgets(Locale locale, int pageNumber) {
    ModelAndView widgetsManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_VIEW, locale);
    LOGGER.info("Construction des widgets pour la page {} ", BACK_PAGE.WIDGET_VIEW.name());

    PageWrapper<WidgetDTO> pagedWidgetDTOWrapped = computePageWrapper(locale, pageNumber);

    widgetsManager.addObject("wrappedWidgets", pagedWidgetDTOWrapped);

    return widgetsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateWidget(Locale locale) {
    ModelAndView widgetManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(CREATE_FORM, computeCreateForm());
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
        .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }

  WidgetCreateForm computeCreateForm() {
    return WidgetCreateFormBuilder.create().build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidget(Locale locale, String widgetId,
      String personalizationLanguageCode) {

    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView widgetManager = super.computeModelAndViewForBackPage(BACK_PAGE.WIDGET_UPDATE, locale);

    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    LOGGER.info("Construction du formulaire de creation des widgets ");
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
        .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(widget.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) widgetManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return widgetManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetMain(Locale locale, String widgetId,
      String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_main");
    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));
    List<String> types = widgetProviders.getPlugins().stream().map(plugin -> plugin.getWidgetType())
        .collect(Collectors.toList());
    widgetManager.addObject(WIDGET_TYPES, types);
    return widgetManager;
  }

  WidgetUpdateForm computeUpdateForm(WidgetDTO widget, String personalizationLanguageCode) {
    return WidgetUpdateFormBuilder.create().creationDate(widget.getCreationDate()).entityId(widget.getEntityId())
        .id(widget.getId()).personalization(widget.getPersonalization()).modificationDate(widget.getModificationDate())
        .name(widget.getName()).type(widget.getType()).localeCode(personalizationLanguageCode).build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateWidgetPersonalization(Locale locale, String widgetId,
      String personalizationLanguageCode) {
    if (!StringUtils.hasText(personalizationLanguageCode)) {
      personalizationLanguageCode = locale.getLanguage();
    }
    ModelAndView widgetManager = new ModelAndView("back/widgets/edit/tab_personalization");
    widgetManager.addObject(LOCALES, availableLocales);
    WidgetDTO widget = widgetService.getEntity(Long.parseLong(widgetId), personalizationLanguageCode);
    widgetManager.addObject(UPDATE_FORM, computeUpdateForm(widget, personalizationLanguageCode));
    List<? extends BaseDTO> linkableEntities = widgetProviders.getPluginFor(widget.getType()).getLinkableEntities();
    widgetManager.addObject(LINKABLE_ENTITIES, linkableEntities);
    widgetManager.addObject(TOOLTIP_KEY, computeToolTipKey(widget.getType()));
    widgetManager.addObject(MACROS_KEY, computePersonalizationMacros());
    return widgetManager;
  }

  protected List<String> computePersonalizationMacros() {
    return Arrays.asList("card", "row", "col", "nav", "collapse", "pagination", "button");
  }

  @Override
  protected String getBaseUrl() {
    return "manager/widgets";
  }

  @Override
  protected String getItemLink() {
    return "/manager/widgets/";
  }

  @Override
  protected Page<WidgetDTO> computeEntries(Locale locale, int pageNumber) {
    List<WidgetDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Direction.ASC, "name"));
    Page<WidgetDTO> pagedWidgetDTOEntries = widgetService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedWidgetDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedWidgetDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedWidgetDTOEntries.getTotalElements());
  }

  String computeToolTipKey(String widgetType) {
    WidgetProviderPlugin widgetProviderPlugin = widgetProviders.getPluginFor(widgetType);
    return widgetProviderPlugin.getTooltipKey();
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:widgets:create";
  }
}
