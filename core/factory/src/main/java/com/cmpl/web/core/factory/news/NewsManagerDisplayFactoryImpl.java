package com.cmpl.web.core.factory.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.news.NewsContentDTO;
import com.cmpl.web.core.news.NewsContentRequest;
import com.cmpl.web.core.news.NewsContentRequestBuilder;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryRequest;
import com.cmpl.web.core.news.NewsEntryRequestBuilder;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.news.NewsImageDTO;
import com.cmpl.web.core.news.NewsImageRequest;
import com.cmpl.web.core.news.NewsImageRequestBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

/**
 * Implementation de l'interface pour la factory des pages d'actualite sur le back
 * 
 * @author Louis
 *
 */
public class NewsManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<NewsEntryDTO>
    implements NewsManagerDisplayFactory {

  private final NewsEntryService newsEntryService;
  private final ContextHolder contextHolder;

  public NewsManagerDisplayFactoryImpl(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.newsEntryService = newsEntryService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(Locale locale, int pageNumber) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_VIEW, locale);
    LOGGER.info("Construction des entrées de blog pour la page {}", BACK_PAGE.NEWS_VIEW.name());

    PageWrapper<NewsEntryDTO> pagedNewsWrapped = computePageWrapper(locale, pageNumber);

    newsManager.addObject("wrappedNews", pagedNewsWrapped);

    return newsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPageCreateNews(Locale locale) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_CREATE, locale);
    LOGGER.info("Construction du formulaire d'entrées de blog pour la page {}", BACK_PAGE.NEWS_CREATE.name());
    newsManager.addObject("newsFormBean", computeNewsRequestForCreateForm());

    return newsManager;
  }

  @Override
  protected Page<NewsEntryDTO> computeEntries(Locale locale, int pageNumber) {
    List<NewsEntryDTO> newsEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    return pagedNewsEntries;
  }

  @Override
  public ModelAndView computeModelAndViewForOneNewsEntry(Locale locale, String newsEntryId) {
    ModelAndView newsManager = super.computeModelAndViewForBackPage(BACK_PAGE.NEWS_UPDATE, locale);
    newsManager.addObject("newsFormBean", computeNewsRequestForEditForm(newsEntryId));

    return newsManager;
  }

  NewsEntryRequest computeNewsRequestForCreateForm() {
    return NewsEntryRequestBuilder.create().content(NewsContentRequestBuilder.create().build())
        .image(NewsImageRequestBuilder.create().build()).build();
  }

  NewsEntryRequest computeNewsRequestForEditForm(String newsEntryId) {

    NewsEntryDTO dto = newsEntryService.getEntity(Long.parseLong(newsEntryId));

    if (dto.getNewsImage() == null) {
      dto.setNewsImage(new NewsImageDTO());
    }
    if (dto.getNewsContent() == null) {
      dto.setNewsContent(new NewsContentDTO());
    }

    return computeNewsEntryRequest(dto);

  }

  NewsEntryRequest computeNewsEntryRequest(NewsEntryDTO dto) {
    return NewsEntryRequestBuilder.create().author(dto.getAuthor()).tags(dto.getTags()).title(dto.getTitle())
        .content(computeNewsContentRequest(dto)).image(computeNewsImageRequest(dto)).id(dto.getId())
        .creationDate(dto.getCreationDate()).modificationDate(dto.getModificationDate()).build();
  }

  NewsImageRequest computeNewsImageRequest(NewsEntryDTO dto) {
    return NewsImageRequestBuilder.create().alt(dto.getNewsImage().getAlt()).media(dto.getNewsImage().getMedia())
        .id(dto.getNewsImage().getId()).creationDate(dto.getNewsImage().getCreationDate())
        .modificationDate(dto.getNewsImage().getModificationDate()).legend(dto.getNewsImage().getLegend()).build();
  }

  NewsContentRequest computeNewsContentRequest(NewsEntryDTO dto) {
    return NewsContentRequestBuilder.create().content(dto.getNewsContent().getContent())
        .id(dto.getNewsContent().getId()).creationDate(dto.getNewsContent().getCreationDate())
        .modificationDate(dto.getNewsContent().getModificationDate()).build();
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/news";
  }

  @Override
  protected String getItemLink() {
    return "/manager/news/";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:news:create";
  }
}
