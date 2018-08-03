package com.cmpl.web.manager.ui.core.administration.user;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.cmpl.web.core.common.notification.StompPrincipal;

public class StompHandshakeHandler extends DefaultHandshakeHandler {

  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CurrentUser principal = (CurrentUser) authentication.getPrincipal();
    return new StompPrincipal(principal.getUsername());
  }

}
