package com.cmpl.web.core.factory.menu;

import java.util.*;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class MenuWidgetProvider implements WidgetProviderPlugin {

  private final MenuFactory menuFactory;
  private final PageService pageService;

  public MenuWidgetProvider(MenuFactory menuFactory, PageService pageService) {
    this.menuFactory = menuFactory;
    this.pageService = pageService;
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {
    Map<String, Object> widgetModel = new HashMap<>();

    widgetModel.put("menuItems", computeMenuItems(pageService.getPageByName(pageName, locale.getLanguage()), locale));

    return widgetModel;
  }

  @Override
  public List<? extends BaseDTO> getLinkableEntities() {
    return new ArrayList<>();
  }

  List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    return menuFactory.computeMenuItems(page, locale);
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }

    return "widgets/menu";

  }

  @Override
  public String getWidgetType() {
    return "MENU";
  }

  @Override
  public String getTooltipKey() {
    return "widget.menu.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
