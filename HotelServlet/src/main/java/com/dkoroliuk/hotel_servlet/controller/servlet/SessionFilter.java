package com.dkoroliuk.hotel_servlet.controller.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.util.Util;

/**
 * Session filter
 */
@WebFilter("/*")
public class SessionFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.getAttribute("currentUserId") == null) {
			session.setAttribute("currentUserId", 0);
			session.setAttribute("currentUserRole", "guest");
			session.setAttribute("roomEditVis", "none");
			session.setAttribute("roomOrderVis", "none");
			session.setAttribute("roomDeleteVis", "none");
			session.setAttribute("makeRequestVis", "none");
			session.setAttribute("isEnable", Boolean.FALSE);
			Locale locale = Util.defineLocale(req);
			session.setAttribute("locale", locale);
		}

		chain.doFilter(request, response);
	}

}
