package com.cmpl.web.core.widget;

import com.cmpl.web.core.widget.page.WidgetPageCreateForm;
import com.cmpl.web.core.widget.page.WidgetPageDTO;
import com.cmpl.web.core.widget.page.WidgetPageResponse;

public interface WidgetTranslator {

  WidgetDTO fromCreateFormToDTO(WidgetCreateForm form);

  WidgetResponse fromDTOToResponse(WidgetDTO dto);

  WidgetPageDTO fromCreateFormToDTO(WidgetPageCreateForm form);

  WidgetPageResponse fromDTOToResponse(WidgetPageDTO dto);

}
