package com.cmpl.web.core.style;

public interface StyleTranslator {

  StyleDTO fromUpdateFormToDTO(StyleForm form);

  StyleResponse fromDTOToResponse(StyleDTO dto);
}
