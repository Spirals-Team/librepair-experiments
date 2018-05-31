package com.cmpl.web.modules.facebook.factory;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.ImportablePost;

/**
 * Implementation de l'interface de factory pour le spages back de facebook
 * 
 * @author Louis
 *
 */
public class FacebookDisplayFactoryImpl extends BackDisplayFactoryImpl implements FacebookDisplayFactory {

  private final FacebookAdapter facebookAdapter;

  public FacebookDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      FacebookAdapter facebookAdapter, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.facebookAdapter = facebookAdapter;
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookAccessPage(Locale locale) {

    boolean isConnected = isAlreadyConnected();
    if (isConnected) {
      LOGGER.info("Redirection vers l'import car déjà connecté");
      return computeModelAndViewForFacebookImportPage(locale);
    }

    return super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_ACCESS, locale);
  }

  @Override
  public ModelAndView computeModelAndViewForFacebookImportPage(Locale locale) {

    ModelAndView facebookImport = super.computeModelAndViewForBackPage(BACK_PAGE.FACEBOOK_IMPORT, locale);

    boolean isConnected = isAlreadyConnected();
    if (!isConnected) {
      LOGGER.info("Redirection vers l'acces car connexion en timeout");
      return computeModelAndViewForFacebookAccessPage(locale);
    }
    facebookImport.addObject("feeds", computeRecentFeeds());
    return facebookImport;

  }

  List<ImportablePost> computeRecentFeeds() {
    return facebookAdapter.getRecentFeed();
  }

  boolean isAlreadyConnected() {
    return facebookAdapter.isAlreadyConnected();
  }

}
