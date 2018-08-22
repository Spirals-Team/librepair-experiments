package com.hedvig.botService.web;

import io.sentry.Sentry;
import io.sentry.event.UserBuilder;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogRequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      // Setup MDC data:

      if (HttpServletRequest.class.isInstance(request)) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String hedvigToken = httpRequest.getHeader("hedvig.token");
        if (hedvigToken != null) {
          MDC.put("memberId", hedvigToken);
          Sentry.getContext().setUser(new UserBuilder().setId(hedvigToken).build());
        }
      }
      chain.doFilter(request, response);
    } finally {
      // Tear down MDC data:
      // ( Important! Cleans up the ThreadLocal data again )
      MDC.clear();
    }
  }

  @Override
  public void destroy() {}
}
