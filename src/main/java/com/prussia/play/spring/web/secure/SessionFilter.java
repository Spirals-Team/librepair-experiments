package com.prussia.play.spring.web.secure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.prussia.play.spring.domain.po.Quote;
import com.prussia.play.spring.domain.vo.Customer;
import com.prussia.play.spring.domain.vo.Value;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
public class SessionFilter extends GenericFilterBean {

	 
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();

		log.info("Customer session = {}", session != null ? session.getId() : null);
		Customer user = session != null ? (Customer) session.getAttribute("USER_SESSION") : null;
		log.info("Customer = {}", user);

		String url = request.getRequestURI();
		String page = url.substring(url.lastIndexOf("/"));

		log.info("---------- URL: " + url + "; page: " + page);

		 

		if (user == null) {
			log.info("---------- Rejected url: {}", url);
			Quote result = new Quote();
			result.setType("TIMEOUT");
			Value value = new Value();
			value.setQuote("The authorization is timeout");
			response.getWriter().print(result);
			return;
		}

		chain.doFilter(req, res);
	}

}
