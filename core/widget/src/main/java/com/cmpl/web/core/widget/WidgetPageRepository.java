package com.cmpl.web.core.widget;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.common.repository.BaseRepository;

@Repository
public interface WidgetPageRepository extends BaseRepository<WidgetPage> {

  List<WidgetPage> findByPageId(String pageId);

  List<WidgetPage> findByWidgetId(String widgetId);

  WidgetPage findByPageIdAndWidgetId(String pageId, String widgetId);

}
