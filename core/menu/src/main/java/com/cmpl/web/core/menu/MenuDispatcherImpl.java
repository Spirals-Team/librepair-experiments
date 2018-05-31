package com.cmpl.web.core.menu;

import java.util.Locale;

import org.springframework.util.StringUtils;

import com.cmpl.web.core.common.error.Error;
import com.cmpl.web.core.page.PageDTO;
import com.cmpl.web.core.page.PageService;

public class MenuDispatcherImpl implements MenuDispatcher {

  private final MenuValidator validator;
  private final MenuTranslator translator;
  private final MenuService menuService;
  private final PageService pageService;

  public MenuDispatcherImpl(MenuValidator validator, MenuTranslator translator, MenuService menuService,
      PageService pageService) {
    this.validator = validator;
    this.translator = translator;
    this.menuService = menuService;
    this.pageService = pageService;
  }

  @Override
  public MenuResponse createEntity(MenuCreateForm form, Locale locale) {

    Error error = validator.validateCreate(form, locale);

    if (error != null) {
      return MenuResponseBuilder.create().error(error).build();
    }

    MenuDTO menuToCreate = translator.fromCreateFormToDTO(form);

    if (!StringUtils.hasText(menuToCreate.getParentId()) && StringUtils.hasText(menuToCreate.getPageId())) {
      PageDTO page = pageService.getEntity(Long.valueOf(menuToCreate.getPageId()));
      menuToCreate.setLabel(page.getMenuTitle());
      menuToCreate.setHref("/pages/" + page.getName());
    }

    MenuDTO createdMenu = menuService.createEntity(menuToCreate);
    return translator.fromDTOToResponse(createdMenu);
  }

  @Override
  public MenuResponse updateEntity(MenuUpdateForm form, Locale locale) {

    Error error = validator.validateUpdate(form, locale);

    if (error != null) {
      return MenuResponseBuilder.create().error(error).build();
    }

    MenuDTO menuToUpdate = menuService.getEntity(form.getId());
    MenuDTOBuilder menuToUpdateBuilder = MenuDTOBuilder.create().href(form.getHref()).label(form.getLabel())
        .orderInMenu(form.getOrderInMenu()).pageId(form.getPageId()).parentId(form.getParentId())
        .title(form.getTitle());

    if (!StringUtils.hasText(menuToUpdate.getParentId()) && StringUtils.hasText(menuToUpdate.getPageId())) {
      PageDTO page = pageService.getEntity(Long.valueOf(menuToUpdate.getPageId()));
      menuToUpdateBuilder.label(page.getMenuTitle()).href("/pages/" + page.getName());
    }
    menuToUpdateBuilder.id(menuToUpdate.getId()).creationDate(menuToUpdate.getCreationDate())
        .modificationDate(menuToUpdate.getModificationDate());
    MenuDTO updatedMenu = menuService.updateEntity(menuToUpdateBuilder.build());

    return translator.fromDTOToResponse(updatedMenu);
  }

  @Override
  public MenuResponse deleteEntity(String menuId, Locale locale) {
    menuService.deleteEntity(Long.parseLong(menuId));
    return MenuResponseBuilder.create().build();
  }

}
