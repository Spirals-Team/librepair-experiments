package ru.job4j.servlets;

import org.apache.log4j.Logger;
import ru.job4j.servlets.utils.AppUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session;
		if (req.getRequestURI().contains("/login")) {
			session = req.getSession(false);
			if (session != null && AppUtils.getLoginedUser(req.getSession()) != null) {
				resp.sendRedirect("/users");
				return;
			}
			chain.doFilter(request, response);
		} else {
			if (AppUtils.getLoginedUser(req.getSession()) == null) {
				resp.sendRedirect("/login");
				return;
			}
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
}
