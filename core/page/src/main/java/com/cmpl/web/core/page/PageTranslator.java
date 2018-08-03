package com.cmpl.web.core.page;

public interface PageTranslator {

  PageDTO fromCreateFormToDTO(PageCreateForm form);

  PageResponse fromDTOToResponse(PageDTO dto);

}
