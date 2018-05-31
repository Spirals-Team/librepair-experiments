package com.cmpl.web.core.menu;


public interface MenuTranslator {

  MenuDTO fromCreateFormToDTO(MenuCreateForm form);

  MenuResponse fromDTOToResponse(MenuDTO dto);
}
