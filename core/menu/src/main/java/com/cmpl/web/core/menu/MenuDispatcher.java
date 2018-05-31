package com.cmpl.web.core.menu;

import java.util.Locale;

public interface MenuDispatcher {

  MenuResponse createEntity(MenuCreateForm form, Locale locale);

  MenuResponse updateEntity(MenuUpdateForm form, Locale locale);

  MenuResponse deleteEntity(String menuId, Locale locale);

}
