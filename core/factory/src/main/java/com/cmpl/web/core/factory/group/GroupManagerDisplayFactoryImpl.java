package com.cmpl.web.core.factory.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.group.GroupCreateForm;
import com.cmpl.web.core.group.GroupDTO;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.group.GroupUpdateForm;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.page.BACK_PAGE;

public class GroupManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<GroupDTO>
    implements GroupManagerDisplayFactory {

  private final GroupService groupService;
  private final ContextHolder contextHolder;

  public GroupManagerDisplayFactoryImpl(GroupService groupService, ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      PluginRegistry<Privilege, String> privileges, Set<Locale> availableLocales, MembershipService membershipService) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales, groupService, membershipService);
    this.groupService = Objects.requireNonNull(groupService);
    this.contextHolder = Objects.requireNonNull(contextHolder);
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllGroups(Locale locale, int pageNumber) {
    ModelAndView groupsManager = super.computeModelAndViewForBackPage(BACK_PAGE.GROUP_VIEW, locale);
    LOGGER.info("Construction des groupes pour la page {} ", BACK_PAGE.GROUP_VIEW.name());

    PageWrapper<GroupDTO> pagedGroupDTOWrapped = computePageWrapper(locale, pageNumber);

    groupsManager.addObject("wrappedGroups", pagedGroupDTOWrapped);

    return groupsManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateGroup(Locale locale) {
    ModelAndView groupManager = super.computeModelAndViewForBackPage(BACK_PAGE.GROUP_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des groupes");

    GroupCreateForm form = new GroupCreateForm();

    groupManager.addObject("createForm", form);

    return groupManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateGroup(Locale locale, String groupId) {
    ModelAndView groupManager = super.computeModelAndViewForBackPage(BACK_PAGE.GROUP_UPDATE, locale);
    LOGGER.info("Construction du groupe pour la page {} ", BACK_PAGE.GROUP_UPDATE.name());
    GroupDTO group = groupService.getEntity(Long.parseLong(groupId));
    GroupUpdateForm form = new GroupUpdateForm(group);

    groupManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(group.getName()).build();
    BreadCrumb breadCrumb = (BreadCrumb) groupManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return groupManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateGroupMain(Locale locale, String groupId) {
    ModelAndView groupManager = new ModelAndView("back/groups/edit/tab_main");
    LOGGER.info("Construction du groupe pour la page {} ", BACK_PAGE.GROUP_UPDATE.name());
    GroupDTO group = groupService.getEntity(Long.parseLong(groupId));
    GroupUpdateForm form = new GroupUpdateForm(group);

    groupManager.addObject("updateForm", form);

    return groupManager;
  }

  @Override
  protected String getBaseUrl() {
    return "/manager/groups";
  }

  @Override
  protected String getItemLink() {
    return "/manager/groups/";
  }

  @Override
  protected Page<GroupDTO> computeEntries(Locale locale, int pageNumber) {
    List<GroupDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Direction.ASC, "name"));
    Page<GroupDTO> pagedGroupDTOEntries = groupService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedGroupDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedGroupDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedGroupDTOEntries.getTotalElements());
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "administration:groups:create";
  }

  @Override
  protected String getCreateItemLink() {
    return getBaseUrl() + "/_create";
  }
}
