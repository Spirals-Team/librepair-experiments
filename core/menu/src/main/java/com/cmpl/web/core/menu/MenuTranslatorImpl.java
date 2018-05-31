package com.cmpl.web.core.menu;

public class MenuTranslatorImpl implements MenuTranslator {

  @Override
  public MenuDTO fromCreateFormToDTO(MenuCreateForm form) {
    return MenuDTOBuilder.create().href(form.getHref()).pageId(form.getPageId()).parentId(form.getParentId())
        .label(form.getLabel()).title(form.getTitle()).orderInMenu(form.getOrderInMenu()).build();
  }

  @Override
  public MenuResponse fromDTOToResponse(MenuDTO dto) {
    return MenuResponseBuilder.create().menu(dto).build();
  }

}
