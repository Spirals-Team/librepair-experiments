package com.cmpl.web.core.page;

public class PageTranslatorImpl implements PageTranslator {

  @Override
  public PageDTO fromCreateFormToDTO(PageCreateForm form) {
    return PageDTOBuilder.create().menuTitle(form.getMenuTitle()).name(form.getName()).footer(form.getFooter())
        .meta(form.getMeta()).header(form.getHeader()).body(form.getBody()).build();
  }

  @Override
  public PageResponse fromDTOToResponse(PageDTO dto) {
    return PageResponseBuilder.create().page(dto).build();
  }

}
