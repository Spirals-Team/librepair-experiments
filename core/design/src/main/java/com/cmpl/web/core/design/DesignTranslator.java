package com.cmpl.web.core.design;

public interface DesignTranslator {

  DesignDTO fromCreateFormToDTO(DesignCreateForm form);

  DesignResponse fromDTOToResponse(DesignDTO dto);

}
