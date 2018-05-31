package com.cmpl.web.core.factory.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.association_user_role.AssociationUserRoleCreateFormBuilder;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDTO;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.resource.PageWrapper;
import com.cmpl.web.core.factory.AbstractBackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.RoleDTO;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserCreateForm;
import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.user.UserUpdateForm;

public class UserManagerDisplayFactoryImpl extends AbstractBackDisplayFactoryImpl<UserDTO>
    implements UserManagerDisplayFactory {

  private final UserService userService;
  private final RoleService roleService;
  private final AssociationUserRoleService assocationUserRoleService;
  private final ContextHolder contextHolder;

  public UserManagerDisplayFactoryImpl(UserService userService, RoleService roleService,
      AssociationUserRoleService assocationUserRoleService, ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
    this.userService = userService;
    this.roleService = roleService;
    this.assocationUserRoleService = assocationUserRoleService;
    this.contextHolder = contextHolder;
  }

  @Override
  public ModelAndView computeModelAndViewForViewAllUsers(Locale locale, int pageNumber) {
    ModelAndView usersManager = super.computeModelAndViewForBackPage(BACK_PAGE.USER_VIEW, locale);
    LOGGER.info("Construction des users pour la page {} ", BACK_PAGE.USER_VIEW.name());

    PageWrapper<UserDTO> pagedUserDTOWrapped = computePageWrapper(locale, pageNumber);

    usersManager.addObject("wrappedUsers", pagedUserDTOWrapped);

    return usersManager;
  }

  @Override
  public ModelAndView computeModelAndViewForCreateUser(Locale locale) {
    ModelAndView userManager = super.computeModelAndViewForBackPage(BACK_PAGE.USER_CREATE, locale);
    LOGGER.info("Construction du formulaire de creation des users");

    UserCreateForm form = new UserCreateForm();

    userManager.addObject("createForm", form);

    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUser(Locale locale, String userId) {
    ModelAndView userManager = super.computeModelAndViewForBackPage(BACK_PAGE.USER_UPDATE, locale);
    LOGGER.info("Construction du user pour la page {} ", BACK_PAGE.USER_UPDATE.name());
    UserDTO user = userService.getEntity(Long.parseLong(userId));
    UserUpdateForm form = new UserUpdateForm(user);

    userManager.addObject("updateForm", form);

    BreadCrumbItem item = BreadCrumbItemBuilder.create().href("#").text(user.getLogin()).build();
    BreadCrumb breadCrumb = (BreadCrumb) userManager.getModel().get("breadcrumb");
    if (canAddBreadCrumbItem(breadCrumb, item)) {
      breadCrumb.getItems().add(item);
    }

    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUserMain(Locale locale, String userId) {
    ModelAndView userManager = new ModelAndView("back/users/edit/tab_main");
    LOGGER.info("Construction du user pour la page {} ", BACK_PAGE.USER_UPDATE.name());
    UserDTO user = userService.getEntity(Long.parseLong(userId));
    UserUpdateForm form = new UserUpdateForm(user);

    userManager.addObject("updateForm", form);
    return userManager;
  }

  @Override
  public ModelAndView computeModelAndViewForUpdateUserRoles(Locale locale, String userId) {
    ModelAndView userManager = new ModelAndView("back/users/edit/tab_roles");
    LOGGER.info("Construction des roles pour la page {} ", BACK_PAGE.USER_UPDATE.name());

    List<RoleDTO> associatedRoles = new ArrayList<>();
    List<AssociationUserRoleDTO> associationUserRoles = assocationUserRoleService.findByUserId(userId);
    associationUserRoles
        .forEach(association -> associatedRoles.add(roleService.getEntity(Long.parseLong(association.getRoleId()))));

    List<RoleDTO> linkableRoles = roleService.getEntities().stream()
        .filter(role -> !associatedRoles.stream().filter(associatedRole -> associatedRole.getId().equals(role.getId()))
            .map(filteredRole -> filteredRole.getId()).collect(Collectors.toList()).contains(role.getId()))
        .collect(Collectors.toList());

    userManager.addObject("linkedRoles", associatedRoles);
    userManager.addObject("linkableRoles", linkableRoles);
    userManager.addObject("createForm", AssociationUserRoleCreateFormBuilder.create().userId(userId).build());
    return userManager;
  }

  @Override
  protected Page<UserDTO> computeEntries(Locale locale, int pageNumber) {
    List<UserDTO> pageEntries = new ArrayList<>();

    PageRequest pageRequest = PageRequest.of(pageNumber, contextHolder.getElementsPerPage(),
        new Sort(Direction.ASC, "login"));
    Page<UserDTO> pagedUserDTOEntries = userService.getPagedEntities(pageRequest);
    if (CollectionUtils.isEmpty(pagedUserDTOEntries.getContent())) {
      return new PageImpl<>(pageEntries);
    }

    pageEntries.addAll(pagedUserDTOEntries.getContent());

    return new PageImpl<>(pageEntries, pageRequest, pagedUserDTOEntries.getTotalElements());
  }

  @Override
  protected String getBaseUrl() {
    return "manager/users";
  }

  @Override
  protected String getItemLink() {
    return "/manager/users/";
  }

  @Override
  protected String getCreateItemPrivilege() {
    return "administration:users:create";
  }
}
