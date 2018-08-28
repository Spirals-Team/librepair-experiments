package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 25.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        if (request.getRequestURI().contains("/signin")) {
            chain.doFilter(req, resp);
        } else {
            if (session.getAttribute("login") == null) {
                ((HttpServletResponse) resp).sendRedirect(String.format("%s/signin", request.getContextPath()));
                return;
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
