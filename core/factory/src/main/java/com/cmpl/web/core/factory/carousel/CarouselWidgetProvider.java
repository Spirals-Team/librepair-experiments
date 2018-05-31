package com.cmpl.web.core.factory.carousel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.carousel.CarouselDTO;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetDTO;

public class CarouselWidgetProvider implements WidgetProviderPlugin {

  private final CarouselService carouselService;

  public CarouselWidgetProvider(CarouselService carouselService) {
    this.carouselService = carouselService;
  }

  @Override
  public Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber) {

    if (!StringUtils.hasText(widget.getEntityId())) {
      return new HashMap<>();
    }
    Map<String, Object> widgetModel = new HashMap<>();
    CarouselDTO carousel = carouselService.getEntity(Long.valueOf(widget.getEntityId()));
    widgetModel.put("carousel", carousel);

    return widgetModel;
  }

  @Override
  public List<CarouselDTO> getLinkableEntities() {
    return carouselService.getEntities();
  }

  @Override
  public String computeWidgetTemplate(WidgetDTO widget, Locale locale) {
    if (StringUtils.hasText(widget.getPersonalization())) {
      return "widget_" + widget.getName() + "_" + locale.getLanguage();
    }
    return "widgets/carousel";
  }

  @Override
  public String getWidgetType() {
    return "CAROUSEL";
  }

  @Override
  public String getTooltipKey() {
    return "widget.carousel.tooltip";
  }

  @Override
  public boolean supports(String delimiter) {
    return getWidgetType().equals(delimiter);
  }
}
