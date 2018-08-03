package com.cmpl.web.core.widget.page;

import java.util.List;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.models.WidgetPage;

public class WidgetPageServiceImpl extends BaseServiceImpl<WidgetPageDTO, WidgetPage> implements WidgetPageService {

  private final WidgetPageDAO widgetPageDAO;

  public WidgetPageServiceImpl(WidgetPageDAO widgetPageDAO, WidgetPageMapper widgetPageMapper) {
    super(widgetPageDAO, widgetPageMapper);
    this.widgetPageDAO = widgetPageDAO;
  }

  @Override
  public List<WidgetPageDTO> findByPageId(String pageId) {
    return mapper.toListDTO(widgetPageDAO.findByPageId(pageId));
  }

  @Override
  public List<WidgetPageDTO> findByWidgetId(String widgetId) {
    return mapper.toListDTO(widgetPageDAO.findByWidgetId(widgetId));
  }

  @Override
  public WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId) {
    return mapper.toDTO(widgetPageDAO.findByPageIdAndWidgetId(pageId, widgetId));
  }

  @Override
  public WidgetPageDTO createEntity(WidgetPageDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
