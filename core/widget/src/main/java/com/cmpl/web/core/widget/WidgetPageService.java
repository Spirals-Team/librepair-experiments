package com.cmpl.web.core.widget;

import java.util.List;

import com.cmpl.web.core.common.service.BaseService;

public interface WidgetPageService extends BaseService<WidgetPageDTO> {

  List<WidgetPageDTO> findByPageId(String pageId);

  List<WidgetPageDTO> findByWidgetId(String widgetId);

  WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId);

}
