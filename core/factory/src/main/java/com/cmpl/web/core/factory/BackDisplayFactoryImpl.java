package com.cmpl.web.core.factory;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.page.BACK_PAGE;

/**
 * Implementation de l'interface commune de factory pour le back
 * 
 * @author Louis
 *
 */
public class BackDisplayFactoryImpl extends BaseDisplayFactoryImpl implements BackDisplayFactory {

  protected static final Logger LOGGER = LoggerFactory.getLogger(BackDisplayFactoryImpl.class);

  private final MenuFactory menuFactory;
  private final PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  protected final Set<Locale> availableLocales;

  public BackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales) {
    super(messageSource);
    this.menuFactory = menuFactory;
    this.breadCrumbRegistry = breadCrumbRegistry;
    this.availableLocales = availableLocales;
  }

  @Override
  public ModelAndView computeModelAndViewForBackPage(BACK_PAGE backPage, Locale locale) {

    String backPageName = backPage.name();
    LOGGER.debug("Construction de la page du back {}", backPageName);
    ModelAndView model = computeModelAndViewBaseTile(backPage, locale);

    LOGGER.debug("Construction du menu pour la page {}", backPageName);
    model.addObject("menuItems", computeBackMenuItems(backPage, locale));
    LOGGER.debug("Construction des locales pour la page {}", backPageName);
    model.addObject("locales", availableLocales);
    LOGGER.debug("Construction du fil d'ariane pour la page {}", backPageName);
    model.addObject("breadcrumb", computeBreadCrumb(backPage));
    LOGGER.debug("Construction du lien du back pour la page {}", backPageName);
    model.addObject("hiddenLink", computeHiddenLink(locale));

    LOGGER.debug("Page du back {} prête", backPageName);

    return model;
  }

  public BreadCrumb computeBreadCrumb(BACK_PAGE backPage) {
    BreadCrumb breadCrumbFromRegistry = breadCrumbRegistry.getPluginFor(backPage);
    if (breadCrumbFromRegistry == null) {
      return null;
    }
    return BreadCrumbBuilder.create().items(breadCrumbFromRegistry.getItems()).page(breadCrumbFromRegistry.getPage())
        .build();
  }

  ModelAndView computeModelAndViewBaseTile(BACK_PAGE backPage, Locale locale) {

    if (BACK_PAGE.LOGIN.equals(backPage) || BACK_PAGE.FORGOTTEN_PASSWORD.equals(backPage)
        || BACK_PAGE.CHANGE_PASSWORD.equals(backPage)) {
      return new ModelAndView(computeTileName(backPage.getTile(), locale));
    }

    ModelAndView model = new ModelAndView(computeDecoratorBackTileName(locale));
    model.addObject("content", computeTileName(backPage.getTile(), locale));
    return model;

  }

  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    return menuFactory.computeBackMenuItems(backPage, locale);
  }

}
