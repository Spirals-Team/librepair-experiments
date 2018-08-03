package com.cmpl.web.core.factory;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupDTO;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.membership.MembershipCreateFormBuilder;
import com.cmpl.web.core.membership.MembershipDTO;
import com.cmpl.web.core.membership.MembershipService;
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
  private final MembershipService membershipService;
  private final GroupService groupService;

  public BackDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    super(messageSource);

    this.menuFactory = Objects.requireNonNull(menuFactory);

    this.breadCrumbRegistry = Objects.requireNonNull(breadCrumbRegistry);

    this.availableLocales = Objects.requireNonNull(availableLocales);
    this.membershipService = Objects.requireNonNull(membershipService);
    this.groupService = Objects.requireNonNull(groupService);

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

  @Override
  public ModelAndView computeModelAndViewForMembership(String entityId) {

    ModelAndView model = new ModelAndView("common/back_membership");

    List<MembershipDTO> memberships = membershipService.findByEntityId(Long.parseLong(entityId));
    List<GroupDTO> associatedGroups = memberships.stream()
        .map(membershipDTO -> groupService.getEntity(membershipDTO.getGroupId())).collect(Collectors.toList());
    model.addObject("linkedGroups", associatedGroups);

    List<GroupDTO> linkableGroups = groupService.getEntities().stream()
        .filter(groupDTO -> !associatedGroups.contains(groupDTO)).collect(Collectors.toList());
    model.addObject("linkableGroups", linkableGroups);
    model.addObject("createForm", MembershipCreateFormBuilder.create().entityId(entityId).build());

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

  public ModelAndView computeModelAndViewBaseTile(BACK_PAGE backPage, Locale locale) {

    if (BACK_PAGE.LOGIN.equals(backPage) || BACK_PAGE.FORGOTTEN_PASSWORD.equals(backPage)
        || BACK_PAGE.CHANGE_PASSWORD.equals(backPage)) {
      return new ModelAndView(backPage.getTile());
    }

    ModelAndView model = new ModelAndView("decorator_back");
    model.addObject("content", backPage.getTile());
    return model;

  }

  public List<MenuItem> computeBackMenuItems(BACK_PAGE backPage, Locale locale) {
    return menuFactory.computeBackMenuItems(backPage, locale);
  }

}
