package ru.job4j.crud.servlets;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
public class AuthFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(AuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session;
		if (req.getRequestURI().contains("/signin")
				|| req.getRequestURI().contains("/create")
				|| req.getRequestURI().contains("/jsonCity")) {
			session = req.getSession(false);
			if (session != null
					&& session.getAttribute("activeUser") != null
					&& req.getRequestURI().contains("/signin")) {
				resp.sendRedirect("/users");
				return;
			}
			chain.doFilter(request, response);
		} else {
			session = req.getSession();
			if (session.getAttribute("activeUser") == null) {
				resp.sendRedirect("/signin");
				return;
			}
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
}
