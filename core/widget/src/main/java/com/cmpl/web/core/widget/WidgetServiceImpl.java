package com.cmpl.web.core.widget;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;

@CacheConfig(cacheNames = "widgets")
public class WidgetServiceImpl extends BaseServiceImpl<WidgetDTO, Widget> implements WidgetService {

  private final FileService fileService;
  private final WidgetRepository repository;
  private static final String WIDGET_PREFIX = "widget_";
  private static final String HTML_SUFFIX = ".html";
  private static final String LOCALE_CODE_PREFIX = "_";

  public WidgetServiceImpl(ApplicationEventPublisher publisher, WidgetRepository repository, FileService fileService) {
    super(repository, publisher);
    this.fileService = fileService;
    this.repository = repository;
  }

  @Override
  @Transactional
  @CacheEvict(value = "pagedWidgets", allEntries = true)
  public WidgetDTO createEntity(WidgetDTO dto, String localeCode) {
    WidgetDTO updatedWidget = super.createEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  @Transactional
  @CachePut(key = "#a0.id+'_'+#a1")
  @CacheEvict(value = {"pagedWidgets"}, allEntries = true)
  public WidgetDTO updateEntity(WidgetDTO dto, String localeCode) {
    WidgetDTO updatedWidget = super.updateEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getPersonalization());

    updatedWidget.setPersonalization(dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  @Cacheable(key = "#a0+'_'+#a1")
  public WidgetDTO getEntity(Long widgetId, String localeCode) {
    WidgetDTO fetchedWidget = super.getEntity(widgetId);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(WIDGET_PREFIX + fetchedWidget.getName()
        + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    return fetchedWidget;
  }

  @Override
  protected WidgetDTO toDTO(Widget entity) {
    WidgetDTO dto = WidgetDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected Widget toEntity(WidgetDTO dto) {
    Widget entity = WidgetBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @Cacheable(key = "#a0+'_'+#a1")
  public WidgetDTO findByName(String widgetName, String localeCode) {
    Widget entity = repository.findByName(widgetName);
    if (entity == null) {
      return WidgetDTOBuilder.create().build();
    }
    WidgetDTO fetchedWidget = toDTO(entity);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(WIDGET_PREFIX + fetchedWidget.getName()
        + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    return fetchedWidget;
  }

  @Override
  @Cacheable(value = "pagedWidgets")
  public Page<WidgetDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }
}
