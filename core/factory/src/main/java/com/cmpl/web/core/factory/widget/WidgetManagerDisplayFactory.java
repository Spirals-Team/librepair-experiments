package com.cmpl.web.core.factory.widget;

import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;

public interface WidgetManagerDisplayFactory extends BackDisplayFactory {

  ModelAndView computeModelAndViewForViewAllWidgets(Locale locale, int pageNumber);

  ModelAndView computeModelAndViewForCreateWidget(Locale locale);

  ModelAndView computeModelAndViewForUpdateWidget(Locale locale, String widgetId, String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdateWidgetMain(Locale locale, String widgetId,
      String personalizationLanguageCode);

  ModelAndView computeModelAndViewForUpdateWidgetPersonalization(Locale locale, String widgetId,
      String personalizationLanguageCode);

}
