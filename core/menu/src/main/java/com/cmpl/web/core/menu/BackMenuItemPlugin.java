package com.cmpl.web.core.menu;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

@Qualifier(value = "backMenus")
public interface BackMenuItemPlugin extends Plugin<String> {

}
