package org.w4tracking.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class DefaultSecurityContextFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DefaultSecurityContext.setServletRequest((HttpServletRequest) request);
        try {
            chain.doFilter(request, response);
        } finally {
            DefaultSecurityContext.clearServletRequest();
        }
    }

    @Override
    public void destroy() {
    }

}
