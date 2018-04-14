package com.prussia.play.spring.web.secure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CrossDomainFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUTDELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		response.setDateHeader("Expires", 0);
		
		arg2.doFilter(request, response);

	}

}
