package com.cmpl.web.core.style;

import java.util.Locale;

public class StyleDispatcherImpl implements StyleDispatcher {

  private final StyleService styleService;
  private final StyleTranslator translator;

  public StyleDispatcherImpl(StyleService styleService, StyleTranslator translator) {
    this.styleService = styleService;
    this.translator = translator;
  }

  @Override
  public StyleResponse updateEntity(StyleForm form, Locale locale) {
    StyleDTO dto = translator.fromUpdateFormToDTO(form);
    StyleDTO updatedDTO = styleService.updateEntity(dto);
    return translator.fromDTOToResponse(updatedDTO);
  }

}
