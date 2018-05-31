package com.cmpl.web.core.factory.style;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.media.MediaDTO;
import com.cmpl.web.core.media.MediaDTOBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.Style;
import com.cmpl.web.core.style.StyleDTO;
import com.cmpl.web.core.style.StyleDTOBuilder;
import com.cmpl.web.core.style.StyleForm;
import com.cmpl.web.core.style.StyleService;

public class StyleDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<Style> implements StyleDisplayFactory {

  private final StyleService styleService;
  private final ContextHolder contextHolder;

  public StyleDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource, StyleService styleService,
      ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.styleService = styleService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewStyles(Locale locale) {

    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_VIEW, locale);
    StyleDTO style = styleService.getStyle();

    if (style == null) {
      style = initStyle();
    }

    stylesManager.addObject("style", style);

    return stylesManager;
  }

  StyleDTO initStyle() {
    StyleDTO style = StyleDTOBuilder.create().content("").media(initMedia())
        .id(Math.abs(UUID.randomUUID().getLeastSignificantBits())).build();
    return styleService.createEntity(style);
  }

  MediaDTO initMedia() {
    return MediaDTOBuilder.create().name("styles.css").extension(".css").size(0l).contentType("text/css")
        .src("/public/media/" + "styles.css").id(Math.abs(UUID.randomUUID().getLeastSignificantBits())).build();
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateStyles(Locale locale) {
    ModelAndView stylesManager = super.computeModelAndViewForBackPage(BACK_PAGE.STYLES_UPDATE, locale);
    StyleDTO style = styleService.getStyle();
    if (style == null) {
      style = initStyle();
    }
    StyleForm form = new StyleForm(style);
    stylesManager.addObject("styleForm", form);

    return stylesManager;
  }

  @Override
  protected String getBaseUrl() {
    return null;
  }

  @Override
  protected String getItemLink() {
    return null;
  }

  @Override
  protected Page<Style> computeEntries(Locale locale, int pageNumber) {
    return null;
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "webmastering:style:create";
  }

}
