package com.cmpl.web.core.design;

public class DesignTranslatorImpl implements DesignTranslator {

  @Override
  public DesignDTO fromCreateFormToDTO(DesignCreateForm form) {
    return DesignDTOBuilder.create().websiteId(Long.parseLong(form.getWebsiteId()))
        .styleId(Long.parseLong(form.getStyleId())).build();
  }

  @Override
  public DesignResponse fromDTOToResponse(DesignDTO dto) {
    return DesignResponseBuilder.create().designDTO(dto).build();
  }
}
