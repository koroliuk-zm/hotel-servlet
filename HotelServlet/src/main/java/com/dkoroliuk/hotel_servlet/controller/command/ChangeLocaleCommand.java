package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;

/**
 * Command to change current {@link Locale}
 */
public class ChangeLocaleCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		String language = request.getParameter("language");
		String country = request.getParameter("country");
		Locale locale = new Locale(language, country);

		HttpSession session = request.getSession();
		session.setAttribute("locale", locale);

		Cookie langCookie = new Cookie("language", language);
		Cookie countryCookie = new Cookie("country", country);

		response.addCookie(langCookie);
		response.addCookie(countryCookie);

		request.getRequestDispatcher("main?action=default").forward(request, response);
	}

}
