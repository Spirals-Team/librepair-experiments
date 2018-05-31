package com.cmpl.web.manager.ui.core.user;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.cmpl.web.core.user.UserService;

public class LastConnectionUpdateAuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

  private final UserService userService;

  public LastConnectionUpdateAuthenticationSuccessHandlerImpl(UserService userService) {
    this.userService = userService;
    this.setDefaultTargetUrl("/manager");
    this.setAlwaysUseDefaultTargetUrl(false);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
    userService.updateLastConnection(currentUser.getUser().getId(), LocalDateTime.now());

    super.onAuthenticationSuccess(request, response, authentication);
  }
}
