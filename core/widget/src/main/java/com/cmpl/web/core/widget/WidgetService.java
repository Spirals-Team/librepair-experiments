package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.service.BaseService;

public interface WidgetService extends BaseService<WidgetDTO> {

  WidgetDTO findByName(String widgetName, String localeCode);

  WidgetDTO getEntity(Long widgetId, String localeCode);

  WidgetDTO updateEntity(WidgetDTO dto, String localeCode);

  WidgetDTO createEntity(WidgetDTO dto, String localeCode);

}
