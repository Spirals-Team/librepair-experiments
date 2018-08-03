package com.cmpl.web.core.widget.page;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.dao.BaseDAOImpl;
import com.cmpl.web.core.models.WidgetPage;

public class WidgetPageDAOImpl extends BaseDAOImpl<WidgetPage> implements WidgetPageDAO {

  private final WidgetPageRepository widgetPageRepository;

  public WidgetPageDAOImpl(WidgetPageRepository entityRepository, ApplicationEventPublisher publisher) {
    super(WidgetPage.class, entityRepository, publisher);
    this.widgetPageRepository = entityRepository;
  }

  @Override
  public List<WidgetPage> findByPageId(String pageId) {
    return widgetPageRepository.findByPageId(pageId);
  }

  @Override
  public List<WidgetPage> findByWidgetId(String widgetId) {
    return widgetPageRepository.findByWidgetId(widgetId);
  }

  @Override
  public WidgetPage findByPageIdAndWidgetId(String pageId, String widgetId) {
    return widgetPageRepository.findByPageIdAndWidgetId(pageId, widgetId);
  }
}
