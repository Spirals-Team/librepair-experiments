package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.store.DbStore;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 28.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class EditRoleFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(EditRoleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        session.getAttribute("login");
        int currentRole = DbStore.getInstance().getUserRoleByLogin((String) session.getAttribute("login"));
        if (currentRole != 1) {
            ((HttpServletResponse) resp).sendRedirect(String.format("%s/list", request.getContextPath()));
            return;
        }
        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
