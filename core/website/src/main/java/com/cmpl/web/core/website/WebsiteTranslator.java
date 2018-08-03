package com.cmpl.web.core.website;

public interface WebsiteTranslator {

  WebsiteDTO fromCreateFormToDTO(WebsiteCreateForm form);

  WebsiteDTO fromUpdateFormToDTO(WebsiteUpdateForm form);

  WebsiteResponse fromDTOToResponse(WebsiteDTO dto);
}
