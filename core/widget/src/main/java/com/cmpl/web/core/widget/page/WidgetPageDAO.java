package com.cmpl.web.core.widget.page;

import java.util.List;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.WidgetPage;

public interface WidgetPageDAO extends BaseDAO<WidgetPage> {

  List<WidgetPage> findByPageId(String pageId);

  List<WidgetPage> findByWidgetId(String widgetId);

  WidgetPage findByPageIdAndWidgetId(String pageId, String widgetId);

}
