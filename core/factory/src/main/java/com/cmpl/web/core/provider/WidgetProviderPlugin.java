package com.cmpl.web.core.provider;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

import com.cmpl.web.core.common.dto.BaseDTO;
import com.cmpl.web.core.widget.WidgetDTO;

@Qualifier(value = "widgetProviders")
public interface WidgetProviderPlugin extends Plugin<String> {

  Map<String, Object> computeWidgetModel(WidgetDTO widget, Locale locale, String pageName, int pageNumber);

  List<? extends BaseDTO> getLinkableEntities();

  String computeWidgetTemplate(WidgetDTO widget, Locale locale);

  String getWidgetType();

  String getTooltipKey();
}
