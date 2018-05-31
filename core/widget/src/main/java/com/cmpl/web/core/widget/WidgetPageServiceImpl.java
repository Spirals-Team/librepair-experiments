package com.cmpl.web.core.widget;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;

import com.cmpl.web.core.common.service.BaseServiceImpl;

@CacheConfig(cacheNames = "widgetPages")
public class WidgetPageServiceImpl extends BaseServiceImpl<WidgetPageDTO, WidgetPage> implements WidgetPageService {

  private final WidgetPageRepository widgetPageRepository;

  public WidgetPageServiceImpl(ApplicationEventPublisher publisher, WidgetPageRepository widgetPageRepository) {
    super(widgetPageRepository, publisher);
    this.widgetPageRepository = widgetPageRepository;
  }

  @Override
  protected WidgetPageDTO toDTO(WidgetPage entity) {
    WidgetPageDTO dto = WidgetPageDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected WidgetPage toEntity(WidgetPageDTO dto) {
    WidgetPage entity = WidgetPageBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @Cacheable(value = "forPage", key = "#a0")
  public List<WidgetPageDTO> findByPageId(String pageId) {
    return toListDTO(widgetPageRepository.findByPageId(pageId));
  }

  @Override
  @Cacheable(value = "forWidget", key = "#a0")
  public List<WidgetPageDTO> findByWidgetId(String widgetId) {
    return toListDTO(widgetPageRepository.findByWidgetId(widgetId));
  }

  @Override
  @Cacheable(value = "byPageAndWidget", key = "#a0+'_'+#a1")
  public WidgetPageDTO findByPageIdAndWidgetId(String pageId, String widgetId) {
    return toDTO(widgetPageRepository.findByPageIdAndWidgetId(pageId, widgetId));
  }

  @Override
  @CacheEvict(value = {"forWidget", "forPage", "byPageAndWidget"}, allEntries = true)
  public WidgetPageDTO createEntity(WidgetPageDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @CacheEvict(value = {"forWidget", "forPage", "byPageAndWidget"}, allEntries = true)
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
