package ru.job4j.servlets.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class AuthFilter.
 *
 * @author ifedorenko
 * @since 14.08.2018
 */
public class AuthFilter implements Filter {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURI().contains("/signin")) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession();
            synchronized (session) {
                if (session.getAttribute("login") == null) {
                    ((HttpServletResponse) response).sendRedirect(String.format("%s/signin", req.getContextPath()));
                    return;
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
