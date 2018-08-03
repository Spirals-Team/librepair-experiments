package com.cmpl.web.manager.ui.core.common.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cmpl.web.core.common.user.GroupGrantedAuthority;
import com.cmpl.web.core.membership.MembershipDTO;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.responsibility.ResponsibilityDTO;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.administration.user.CurrentUser;

public class CurrentUserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;
  private final ResponsibilityService responsibilityService;
  private final MembershipService membershipService;
  private final RoleService roleService;

  public CurrentUserDetailsServiceImpl(UserService userService, RoleService roleService,
      ResponsibilityService responsibilityService, MembershipService membershipService) {
    this.userService = Objects.requireNonNull(userService);

    this.roleService = Objects.requireNonNull(roleService);

    this.responsibilityService = Objects.requireNonNull(responsibilityService);

    this.membershipService = Objects.requireNonNull(membershipService);

  }

  @Override
  public CurrentUser loadUserByUsername(String login) throws UsernameNotFoundException {
    UserDTO user = userService.findByLogin(login);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User with email=%s was not found", login));
    }

    List<ResponsibilityDTO> associationsUserRoles = responsibilityService.findByUserId(String.valueOf(user.getId()));
    Set<String> mergedPrivileges = new HashSet<>();

    associationsUserRoles.forEach(associationUserRoleDTO -> {
      mergedPrivileges
          .addAll(roleService.getEntity(Long.parseLong(associationUserRoleDTO.getRoleId())).getPrivileges());
    });

    List<MembershipDTO> associationEntityGroups = membershipService.findByEntityId(user.getId());
    List<GroupGrantedAuthority> groupsGranted = new ArrayList<>();
    associationEntityGroups.forEach(associationEntityGroupDTO -> groupsGranted
        .add(new GroupGrantedAuthority(associationEntityGroupDTO.getGroupId())));

    List<GrantedAuthority> authorities = AuthorityUtils
        .createAuthorityList(mergedPrivileges.toArray(new String[mergedPrivileges.size()]));
    authorities.addAll(groupsGranted);

    return new CurrentUser(user, authorities);
  }
}
