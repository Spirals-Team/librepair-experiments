package com.cmpl.web.core.widget;

import com.cmpl.web.core.common.dao.BaseDAO;
import com.cmpl.web.core.models.Widget;

public interface WidgetDAO extends BaseDAO<Widget> {

  Widget findByName(String widgetName);

}
