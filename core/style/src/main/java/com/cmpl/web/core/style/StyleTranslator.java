package com.cmpl.web.core.style;

public interface StyleTranslator {

  StyleDTO fromUpdateFormToDTO(StyleUpdateForm form);

  StyleDTO fromCreateFormToDTO(StyleCreateForm form);

  StyleResponse fromDTOToResponse(StyleDTO dto);
}
