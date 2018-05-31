package com.cmpl.web.manager.ui.core.user;

import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;

import com.cmpl.web.core.user.UserDTO;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

  private UserDTO user;

  public CurrentUser(UserDTO user) {
    super(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList());
    this.user = user;
  }

  public CurrentUser(UserDTO user, String... privileges) {
    super(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(privileges));
    this.user = user;
  }

  public CurrentUser(UserDTO user, Set<String> privileges) {
    super(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(privileges
        .toArray(new String[privileges.size()])));
    this.user = user;
  }

  public UserDTO getUser() {
    return user;
  }

  public boolean hasPrivilege(String privilege) {
    return getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(privilege));
  }

}
