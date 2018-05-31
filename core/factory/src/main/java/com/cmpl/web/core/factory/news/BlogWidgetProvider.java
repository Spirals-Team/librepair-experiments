package com.cmpl.web.core.factory.news;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.builder.PageWrapperBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class BlogWidgetProvider implements WidgetProviderPlugin {

  private final WebMessageSource messageSource;
  private final ContextHolder contextHolder;
  private final NewsEntryService newsEntryService;

  public BlogWidgetProvider(WebMessageSource messageSource, ContextHolder contextHolder,
      NewsEntryService newsEntryService) {
    this.messageSource = messageSource;
    this.newsEntryService = newsEntryService;
    this.contextHolder = contextHolder;

  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    Map<String, Object> widgetModel = new HashMap<>();

    PageWrapper<NewsEntryDTO> pagedNewsWrapped = computePageWrapperOfNews(widget, locale, pageNumber);

    List<NewsEntryDTO> entries = computeNewsEntriesForPage(pageNumber);
    List<String> entriesIds = new ArrayList<>();
    entries.forEach(entry -> entriesIds.add(String.valueOf(entry.getId())));

    widgetModel.put("wrappedNews", pagedNewsWrapped);
    widgetModel.put("news", entriesIds);
    widgetModel.put("widgetId", String.valueOf(widget.getId()));
    widgetModel.put("emptyMessage", getI18nValue("actualites.empty", locale));

    return widgetModel;
  }

  @Override
  public List<? extends BaseDTO> getLinkableEntities() {
    return new ArrayList<>();
  }

  PageWrapper<NewsEntryDTO> computePageWrapperOfNews(WidgetDTO widget, Locale locale, int pageNumber) {
    Page<NewsEntryDTO> pagedNewsEntries = computeNewsEntries(locale, pageNumber);

    boolean isFirstPage = pagedNewsEntries.isFirst();
    boolean isLastPage = pagedNewsEntries.isLast();
    int totalPages = pagedNewsEntries.getTotalPages();
    int currentPageNumber = pagedNewsEntries.getNumber();

    return new PageWrapperBuilder<NewsEntryDTO>().currentPageNumber(currentPageNumber).firstPage(isFirstPage)
        .lastPage(isLastPage).page(pagedNewsEntries).totalPages(totalPages).pageBaseUrl("/widgets/" + widget.getName())
        .pageLabel(getI18nValue("pagination.page", locale, currentPageNumber + 1, totalPages)).build();
  }

  String getI18nValue(String key, Locale locale, Object... args) {
    return messageSource.getI18n(key, locale, args);
  }

  Page<NewsEntryDTO> computeNewsEntries(Locale locale, int pageNumber) {
    List<NewsEntryDTO> newsEntries = new ArrayList<>();
    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedNewsEntries.getContent())) {
      return new PageImpl<>(newsEntries);
    }

    return pagedNewsEntries;
  }

  List<NewsEntryDTO> computeNewsEntriesForPage(int pageNumber) {

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage());
    Page<NewsEntryDTO> pagedNewsEntries = newsEntryService.getPagedEntities(pageRequest);

    return pagedNewsEntries.getContent();

  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/blog";
  }

  @Override
  public String getWidgetType() {
    return "BLOG";
  }

  @Override
  public String getTooltipKey() {
    return "widget.blog.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
