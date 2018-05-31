package com.cmpl.web.core.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetDTOBuilder;
import com.cmpl.web.core.widget.WidgetPageDTO;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

/**
 * Implementation de l'interface de factory pur generer des model and view pour les pages du site
 * 
 * @author Louis
 *
 */
public class DisplayFactoryImpl extends BaseDisplayFactoryImpl implements DisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(DisplayFactoryImpl.class);
  private final PageService pageService;
  private final NewsEntryService newsEntryService;
  private final WidgetPageService widgetPageService;
  private final WidgetService widgetService;
  private final PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  public DisplayFactoryImpl(WebMessageSource messageSource, PageService pageService, NewsEntryService newsEntryService,
      WidgetPageService widgetPageService, WidgetService widgetService,
      PluginRegistry<WidgetProviderPlugin, String> widgetProviders) {
    super(messageSource);

    this.pageService = pageService;
    this.newsEntryService = newsEntryService;
    this.widgetPageService = widgetPageService;
    this.widgetService = widgetService;
    this.widgetProviders = widgetProviders;
  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber) {
    LOGGER.debug("Construction de la page  {}", pageName);

    PageDTO page = pageService.getPageByName(pageName, locale.getLanguage());

    ModelAndView model = new ModelAndView("decorator");
    model.addObject("content", computePageContent(page, locale));
    LOGGER.debug("Construction du footer pour la page  {}", pageName);
    model.addObject("footerTemplate", computePageFooter(page, locale));
    LOGGER.debug("Construction du header pour la page  {}", pageName);
    model.addObject("header", computePageHeader(page, locale));
    LOGGER.debug("Construction de la meta pour la page  {}", pageName);
    model.addObject("meta", computePageMeta(page, locale));
    LOGGER.debug("Construction du lien du back pour la page {}", pageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.debug("Construction des widgets pour la page {}", pageName);
    List<WidgetPageDTO> widgetPageDTOS = widgetPageService.findByPageId(String.valueOf(page.getId()));
    List<String> widgetIds = new ArrayList<>();
    widgetPageDTOS.forEach(widgetPageDTO -> widgetIds.add(widgetPageDTO.getWidgetId()));
    List<String> widgetNames = new ArrayList<>();
    widgetIds.forEach(widgetId -> widgetNames.add(widgetService.getEntity(Long.parseLong(widgetId)).getName()));

    model.addObject("widgetNames", widgetNames);

    LOGGER.debug("Page {} prête", pageName);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForAMP(String pageName, Locale locale, int pageNumber) {
    LOGGER.debug("Construction de la page amp {}", pageName);

    PageDTO page = pageService.getPageByName(pageName, locale.getLanguage());

    ModelAndView model = new ModelAndView("decorator_amp");
    model.addObject("amp_content", computePageAMPContent(page, locale));

    LOGGER.debug("Page {} prête", pageName);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForBlogEntry(String newsEntryId, String widgetId, Locale locale) {

    LOGGER.debug("Construction de l'entree de blog d'id {}", newsEntryId);

    WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor("BLOG_ENTRY");
    ModelAndView model = new ModelAndView(
        widgetProvider.computeWidgetTemplate(WidgetDTOBuilder.create().build(), locale));
    NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));
    model.addObject("newsBean", newsEntry);

    LOGGER.debug("Entree de blog {}  prête", newsEntryId);

    return model;
  }

  @Override
  public ModelAndView computeModelAndViewForWidget(String widgetName, Locale locale, int pageNumber, String pageName) {

    LOGGER.debug("Construction du wiget {}", widgetName);

    WidgetDTO widget = widgetService.findByName(widgetName, locale.getLanguage());
    ModelAndView model = new ModelAndView(computeWidgetTemplate(widget, locale));

    model.addObject("pageNumber", pageNumber);

    Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale, pageName);
    if (!CollectionUtils.isEmpty(widgetModel)) {
      widgetModel.forEach((key, value) -> model.addObject(key, value));
    }
    model.addObject("widgetName", widget.getName());

    LOGGER.debug("Widget {} prêt", widgetName);

    return model;
  }

  String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
    return widgetProvider.computeWidgetTemplate(widget, locale);
  }

  String computePageContent(PageDTO page, Locale locale) {
    return page.getName() + "_" + locale.getLanguage();
  }

  String computePageAMPContent(PageDTO page, Locale locale) {
    return page.getName() + "_amp_" + locale.getLanguage();
  }

  String computePageHeader(PageDTO page, Locale locale) {
    return page.getName() + "_header_" + locale.getLanguage();
  }

  String computePageMeta(PageDTO page, Locale locale) {
    return page.getName() + "_meta_" + locale.getLanguage();
  }

  String computePageFooter(PageDTO page, Locale locale) {
    return page.getName() + "_footer_" + locale.getLanguage();
  }

  Map<String, Object> computeWidgetModel(WidgetDTO widget, int pageNumber, Locale locale, String pageName) {

    WidgetProviderPlugin widgetProvider = widgetProviders.getPluginFor(widget.getType());
    return widgetProvider.computeWidgetModel(widget, locale, pageName, pageNumber);

  }

}
