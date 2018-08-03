package com.cmpl.web.core.widget;

import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageDTOBuilder;
import com.cmpl.web.core.widget.page.WidgetPageResponse;
import com.cmpl.web.core.widget.page.WidgetPageResponseBuilder;

public class WidgetTranslatorImpl implements WidgetTranslator {

  @Override
  public WidgetDTO fromCreateFormToDTO(WidgetCreateForm form) {
    return WidgetDTOBuilder.create().name(form.getName()).asynchronous(form.getAsynchronous()).type(form.getType())
        .personalization("").build();
  }

  @Override
  public WidgetResponse fromDTOToResponse(WidgetDTO dto) {
    return WidgetResponseBuilder.create().widget(dto).build();
  }

  @Override
  public WidgetPageDTO fromCreateFormToDTO(WidgetPageCreateForm form) {
    return WidgetPageDTOBuilder.create().pageId(form.getPageId()).widgetId(form.getWidgetId()).build();
  }

  @Override
  public WidgetPageResponse fromDTOToResponse(WidgetPageDTO dto) {
    return WidgetPageResponseBuilder.create().widgetPage(dto).build();
  }
}
