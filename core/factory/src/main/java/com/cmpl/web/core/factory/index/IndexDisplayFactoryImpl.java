package com.cmpl.web.core.factory.index;

import java.util.Locale;
import java.util.Set;

import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BACK_PAGE;

public class IndexDisplayFactoryImpl extends BackDisplayFactoryImpl implements IndexDisplayFactory {

  public IndexDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService, membershipService);
  }
}
