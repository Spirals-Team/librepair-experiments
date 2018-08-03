package com.cmpl.web.core.factory;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.design.DesignDTO;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.news.entry.NewsEntryDTO;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.sitemap.SitemapDTO;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetDTO;
import com.cmpl.web.core.widget.WidgetDTOBuilder;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageService;

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
  private final WebsiteService websiteService;
  private final SitemapService sitemapService;
  private final DesignService designService;
  private final StyleService styleService;

  public DisplayFactoryImpl(WebMessageSource messageSource, PageService pageService, NewsEntryService newsEntryService,
      WidgetPageService widgetPageService, WidgetService widgetService,
      PluginRegistry<WidgetProviderPlugin, String> widgetProviders, WebsiteService websiteService,
      SitemapService sitemapService, DesignService designService, StyleService styleService) {
    super(messageSource);

    this.pageService = Objects.requireNonNull(pageService);
    this.newsEntryService = Objects.requireNonNull(newsEntryService);
    this.widgetPageService = Objects.requireNonNull(widgetPageService);
    this.widgetService = Objects.requireNonNull(widgetService);
    this.widgetProviders = Objects.requireNonNull(widgetProviders);
    this.websiteService = Objects.requireNonNull(websiteService);
    this.designService = Objects.requireNonNull(designService);
    this.sitemapService = Objects.requireNonNull(sitemapService);
    this.styleService = Objects.requireNonNull(styleService);

  }

  @Override
  public ModelAndView computeModelAndViewForPage(String pageName, Locale locale, int pageNumber) {
    LOGGER.debug("Construction de la page  {}", pageName);

    PageDTO page = pageService.getPageByName(pageName, locale.getLanguage());
    if (page.getId() == null) {
      return new ModelAndView("404");
    }

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
    List<String> widgetIds = widgetPageDTOS.stream().map(widgetPageDTO -> widgetPageDTO.getWidgetId())
        .collect(Collectors.toList());
    List<String> widgetAsynchronousNames = widgetIds.stream()
        .map(widgetId -> widgetService.getEntity(Long.parseLong(widgetId))).filter(widget -> widget.isAsynchronous())
        .map(widget -> widget.getName()).collect(Collectors.toList());

    List<WidgetDTO> synchronousWidgets = widgetIds.stream()
        .map(widgetId -> widgetService.getEntity(Long.parseLong(widgetId), locale.getLanguage()))
        .filter(widget -> !widget.isAsynchronous()).collect(Collectors.toList());

    synchronousWidgets.forEach(widget -> {

      Map<String, Object> widgetModel = computeWidgetModel(widget, pageNumber, locale, pageName);
      if (!CollectionUtils.isEmpty(widgetModel)) {
        widgetModel.forEach((key, value) -> model.addObject(key, value));
      }

      model.addObject("widget_" + widget.getName(), computeWidgetTemplate(widget, locale));

    });

    model.addObject("pageNumber", pageNumber);
    model.addObject("widgetNames", widgetAsynchronousNames);

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

  @Override
  public ModelAndView computeModelAndViewForWebsitePage(String websiteName, String pageName, Locale locale,
      int pageNumber) {

    LOGGER.debug("Construction de la page  {0} pour le site {1}", pageName, websiteName);
    WebsiteDTO websiteDTO = websiteService.getWebsiteByName(websiteName);
    if (websiteDTO == null) {
      return new ModelAndView("404");
    }

    List<SitemapDTO> sitemaps = sitemapService.findByWebsiteId(websiteDTO.getId());
    List<PageDTO> pages = sitemaps.stream()
        .map(sitemap -> pageService.getEntity(sitemap.getPageId(), locale.getLanguage())).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(pages)) {
      return new ModelAndView("404");
    }

    List<DesignDTO> designs = designService.findByWebsiteId(websiteDTO.getId());
    List<StyleDTO> styles = designs.stream().map(design -> styleService.getEntity(design.getStyleId()))
        .collect(Collectors.toList());

    PageDTO page = pages.get(0);
    ModelAndView model = new ModelAndView("decorator");
    model.addObject("styles", styles);
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
    List<String> widgetNames = widgetPageDTOS.stream()
        .map(widgetPage -> widgetService.getEntity(Long.parseLong(widgetPage.getWidgetId())).getName())
        .collect(Collectors.toList());

    model.addObject("widgetNames", widgetNames);

    LOGGER.debug("Page {} prête", pageName);

    return model;

  }

  @Override
  public ModelAndView computeModelAndViewForWebsiteAMP(String websiteName, String pageName, Locale locale,
      int pageNumber) {
    LOGGER.debug("Construction de la page amp {0} pour le site {1}", pageName, websiteName);

    WebsiteDTO websiteDTO = websiteService.getWebsiteByName(websiteName);
    if (websiteDTO == null) {
      return new ModelAndView("404");
    }

    List<SitemapDTO> sitemaps = sitemapService.findByWebsiteId(websiteDTO.getId());
    List<PageDTO> pages = sitemaps.stream()
        .map(sitemap -> pageService.getEntity(sitemap.getPageId(), locale.getLanguage())).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(pages)) {
      return new ModelAndView("404");
    }

    PageDTO page = pages.get(0);

    ModelAndView model = new ModelAndView("decorator_amp");
    model.addObject("amp_content", computePageAMPContent(page, locale));

    LOGGER.debug("Page {} prête", pageName);

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
