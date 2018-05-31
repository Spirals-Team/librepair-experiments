package com.cmpl.web.core.factory.news;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.news.NewsEntryDTO;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class BlogEntryWidgetProvider implements WidgetProviderPlugin {

  private final NewsEntryService newsEntryService;

  public BlogEntryWidgetProvider(NewsEntryService newsEntryService) {
    this.newsEntryService = newsEntryService;
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    Map<String, Object> widgetModel = new HashMap<>();

    String newsEntryId = widget.getEntityId();
    if (StringUtils.hasText(newsEntryId)) {
      NewsEntryDTO newsEntry = newsEntryService.getEntity(Long.parseLong(newsEntryId));
      widgetModel.put("newsBean", newsEntry);
    }

    return widgetModel;
  }

  @Override
  public List<NewsEntryDTO> getLinkableEntities() {
    return newsEntryService.getEntities();

  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/blog_entry";
  }

  @Override
  public String getWidgetType() {
    return "BLOG_ENTRY";
  }

  @Override
  public String getTooltipKey() {
    return "widget.blog.entry.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
