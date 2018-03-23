package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.association.userrole.UserRoleAssociation;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private User user;

  public UserDetailsImpl(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRoles().stream().map(UserRoleAssociation::getUserRole).collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public boolean isEnabled() {
    return user.getIsEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.getIsBlocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
}