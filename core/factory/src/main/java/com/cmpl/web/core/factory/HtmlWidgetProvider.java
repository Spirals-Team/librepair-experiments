package com.cmpl.web.core.factory;

import java.util.*;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class HtmlWidgetProvider implements WidgetProviderPlugin {
  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    return new HashMap<>();
  }

  @Override
  public List<? extends BaseDTO> getLinkableEntities() {
    return new ArrayList<>();
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/default";
  }

  @Override
  public String getWidgetType() {
    return "HTML";
  }

  @Override
  public String getTooltipKey() {
    return "widget.default.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
