package com.cmpl.web.core.responsibility;

public interface ResponsibilityTranslator {

  ResponsibilityDTO fromCreateFormToDTO(ResponsibilityCreateForm form);

  ResponsibilityResponse fromDTOToResponse(ResponsibilityDTO dto);

}
