package com.cmpl.web.core.website;

public class WebsiteTranslatorImpl implements WebsiteTranslator {
  @Override
  public WebsiteDTO fromCreateFormToDTO(WebsiteCreateForm form) {
    return WebsiteDTOBuilder.create().description(form.getDescription()).secure(form.getSecure().booleanValue())
        .name(form.getName()).build();
  }

  @Override
  public WebsiteDTO fromUpdateFormToDTO(WebsiteUpdateForm form) {
    return WebsiteDTOBuilder.create().description(form.getDescription()).secure(form.getSecure().booleanValue())
        .name(form.getName()).id(form.getId()).modificationDate(form.getModificationDate())
        .modificationUser(form.getModificationUser()).creationUser(form.getCreationUser())
        .creationDate(form.getCreationDate()).build();

  }

  @Override
  public WebsiteResponse fromDTOToResponse(WebsiteDTO dto) {
    return WebsiteResponseBuilder.create().website(dto).build();
  }
}
