package com.prussia.play.spring.web.secure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfHeaderFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		CsrfToken csrf = (CsrfToken) arg0.getAttribute(CsrfToken.class.getName());
		if(csrf != null){
			String token = csrf.getToken();
			arg1.setHeader("X-XSRF-TOKEN", token);
		}
		arg2.doFilter(arg0, arg1);
	}

}
