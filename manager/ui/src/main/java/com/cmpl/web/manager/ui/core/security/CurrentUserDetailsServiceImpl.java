package com.cmpl.web.manager.ui.core.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cmpl.web.core.association_user_role.AssociationUserRoleDTO;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.user.CurrentUser;

public class CurrentUserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;
  private final AssociationUserRoleService associationUserRoleService;
  private final RoleService roleService;

  public CurrentUserDetailsServiceImpl(UserService userService, RoleService roleService,
      AssociationUserRoleService associationUserRoleService) {
    this.userService = userService;
    this.roleService = roleService;
    this.associationUserRoleService = associationUserRoleService;
  }

  @Override
  public CurrentUser loadUserByUsername(String login) throws UsernameNotFoundException {
    UserDTO user = userService.findByLogin(login);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User with email=%s was not found", login));
    }

    List<AssociationUserRoleDTO> associationsUserRoles = associationUserRoleService.findByUserId(String.valueOf(user
        .getId()));
    Set<String> mergedPrivileges = new HashSet<>();
    associationsUserRoles
        .forEach(associationUserRoleDTO -> {
          mergedPrivileges.addAll(roleService.getEntity(Long.parseLong(associationUserRoleDTO.getRoleId()))
              .getPrivileges());
        });

    return new CurrentUser(user, mergedPrivileges);
  }
}
