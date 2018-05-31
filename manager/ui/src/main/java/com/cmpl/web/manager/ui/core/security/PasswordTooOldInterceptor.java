package com.cmpl.web.manager.ui.core.security;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cmpl.web.core.user.UserDTO;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.user.CurrentUser;

public class PasswordTooOldInterceptor extends HandlerInterceptorAdapter {

  private final RedirectStrategy redirectStrategy;
  private final UserService userService;

  public PasswordTooOldInterceptor(UserService userService) {
    this.redirectStrategy = new DefaultRedirectStrategy();
    this.userService = userService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() instanceof CurrentUser) {
      UserDTO user = ((CurrentUser) authentication.getPrincipal()).getUser();
      LocalDateTime lastPasswordModification = user.getLastPasswordModification();

      LocalDateTime threeMonths = LocalDateTime.now().minusMonths(3);
      if (lastPasswordModification.compareTo(threeMonths) <= 0) {
        String token = userService.generatePasswordResetToken(user);
        redirectStrategy.sendRedirect(request, response, "/change_password?token=" + token);
        return false;
      }
    }
    return true;
  }

}
