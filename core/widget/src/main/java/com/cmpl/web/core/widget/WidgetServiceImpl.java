package com.cmpl.web.core.widget;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cmpl.web.core.common.service.BaseServiceImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Widget;

public class WidgetServiceImpl extends BaseServiceImpl<WidgetDTO, Widget> implements WidgetService {

  private final FileService fileService;
  private final WidgetDAO widgetDAO;
  private static final String WIDGET_PREFIX = "widget_";
  private static final String HTML_SUFFIX = ".html";
  private static final String LOCALE_CODE_PREFIX = "_";

  public WidgetServiceImpl(WidgetDAO widgetDAO, WidgetMapper widgetMapper, FileService fileService) {
    super(widgetDAO, widgetMapper);

    this.fileService = Objects.requireNonNull(fileService);
    this.widgetDAO = widgetDAO;
  }

  @Override
  public WidgetDTO createEntity(WidgetDTO dto, String localeCode) {
    WidgetDTO updatedWidget = super.createEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  public WidgetDTO updateEntity(WidgetDTO dto, String localeCode) {
    WidgetDTO updatedWidget = super.updateEntity(dto);

    fileService.saveFileOnSystem(WIDGET_PREFIX + dto.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX,
        dto.getPersonalization());

    updatedWidget.setPersonalization(dto.getPersonalization());

    return updatedWidget;
  }

  @Override
  public WidgetDTO getEntity(Long widgetId, String localeCode) {
    WidgetDTO fetchedWidget = super.getEntity(widgetId);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(
        WIDGET_PREFIX + fetchedWidget.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    return fetchedWidget;
  }

  @Override
  public WidgetDTO findByName(String widgetName, String localeCode) {
    Widget entity = widgetDAO.findByName(widgetName);
    if (entity == null) {
      return WidgetDTOBuilder.create().build();
    }
    WidgetDTO fetchedWidget = mapper.toDTO(entity);
    fetchedWidget.setPersonalization(fileService.readFileContentFromSystem(
        WIDGET_PREFIX + fetchedWidget.getName() + LOCALE_CODE_PREFIX + localeCode + HTML_SUFFIX));
    return fetchedWidget;
  }

  @Override
  public Page<WidgetDTO> getPagedEntities(PageRequest pageRequest) {
    return super.getPagedEntities(pageRequest);
  }
}
