package com.cmpl.web.core.factory.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.BaseFactoryImpl;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.MenuDTO;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageDTO;

/**
 * Implementation de l'interface pour la factory du menu
 * 
 * @author Louis
 *
 */
public class MenuFactoryImpl extends BaseFactoryImpl implements MenuFactory {

  private final MenuService menuService;
  private final BackMenu backMenu;

  public MenuFactoryImpl(WebMessageSource messageSource, MenuService menuService, BackMenu backMenu) {
    super(messageSource);
    this.menuService = menuService;
    this.backMenu = backMenu;
  }

  @Override
  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    List<BackMenuItem> parents = backMenu.getItems().stream().filter(item -> item.getParent() == null)
        .collect(Collectors.toList());
    parents.forEach(parent -> {
      List<BackMenuItem> children = backMenu.getItems().stream().filter(item -> parent.equals(item.getParent()))
          .collect(Collectors.toList());
      List<MenuItem> childrenItems = new ArrayList<>();
      children.forEach(childItem -> childrenItems.add(computeMenuItem(backPage, childItem, locale)));
      menuItems.add(computeMenuItem(backPage, parent, locale, childrenItems, children));
    });
    return menuItems;
  }

  MenuItem computeMenuItem(BACK_PAGE backPage, BackMenuItem item, Locale locale) {
    return MenuItemBuilder.create().href(getI18nValue(item.getHref(), locale))
        .label(getI18nValue(item.getLabel(), locale)).title(getI18nValue(item.getTitle(), locale))
        .subMenuItems(new ArrayList<>()).customCssClass(computeCustomCssClass(backPage, item))
        .iconClass(item.getIconClass()).privilege(item.getPrivilege()).build();
  }

  MenuItem computeMenuItem(BACK_PAGE backPage, BackMenuItem item, Locale locale, List<MenuItem> children,
      List<BackMenuItem> untransformedChildren) {
    return MenuItemBuilder.create().href(getI18nValue(item.getHref(), locale))
        .label(getI18nValue(item.getLabel(), locale)).title(getI18nValue(item.getTitle(), locale))
        .subMenuItems(children).customCssClass(computeCustomCssClass(backPage, item, untransformedChildren))
        .iconClass(item.getIconClass()).privilege(item.getPrivilege()).build();
  }

  String computeCustomCssClass(BACK_PAGE backPage, BackMenuItem item) {
    return isItemActive(backPage, item) ? "active" : "";
  }

  String computeCustomCssClass(BACK_PAGE backPage, BackMenuItem item, List<BackMenuItem> children) {
    return (isItemActive(backPage, item) || isAnyChildActive(backPage, children)) ? "active" : "";
  }

  boolean isItemActive(BACK_PAGE backPage, BackMenuItem item) {
    return backPage.getTitle().equals(item.getTitle());
  }

  boolean isAnyChildActive(BACK_PAGE backPage, List<BackMenuItem> children) {
    List<Boolean> anyTrue = children.stream().map(child -> child.getTitle().equals(backPage.getTitle()))
        .collect(Collectors.toList());
    return anyTrue.contains(Boolean.TRUE);
  }

  @Override
  public List<MenuItem> computeMenuItems(PageDTO page, Locale locale) {
    List<MenuItem> menuItems = new ArrayList<>();
    menuService.getMenus().forEach(menuItem -> menuItems.add(computeMenuItem(page, menuItem)));
    return menuItems;
  }

  MenuItem computeMenuItem(PageDTO page, MenuDTO menu) {
    return MenuItemBuilder.create().href(menu.getHref()).label(menu.getLabel()).title(menu.getTitle())
        .customCssClass(computeCustomCssClass(page, menu)).subMenuItems(computeSubMenuItems(page, menu)).build();
  }

  String computeCustomCssClass(PageDTO page, MenuDTO menu) {
    return page.getId().equals(Long.parseLong(menu.getPageId())) ? "active" : "";
  }

  List<MenuItem> computeSubMenuItems(PageDTO page, MenuDTO menu) {
    List<MenuItem> subMenuItems = new ArrayList<>();
    menu.getChildren().forEach(subMenu -> subMenuItems.add(computeMenuItem(page, subMenu)));
    return subMenuItems;
  }

}
