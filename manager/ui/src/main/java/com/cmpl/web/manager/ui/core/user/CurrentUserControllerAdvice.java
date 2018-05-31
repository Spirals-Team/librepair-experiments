package com.cmpl.web.manager.ui.core.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ControllerAdvice(annotations = ManagerController.class)
public class CurrentUserControllerAdvice {

  @ModelAttribute("currentUser")
  public CurrentUser getCurrentUser(Authentication authentication) {
    return (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof CurrentUser)) ? null
        : (CurrentUser) authentication.getPrincipal();
  }
}
