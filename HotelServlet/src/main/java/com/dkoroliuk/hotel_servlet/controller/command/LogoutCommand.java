package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.util.Util;

/**
 * Logout command
 */
public class LogoutCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession(true);
		session.setAttribute("isEnable", Boolean.TRUE);
		session.setAttribute("currentUserId", 0);
		session.setAttribute("roomEditVis", "none");
		session.setAttribute("roomOrderVis", "none");
		session.setAttribute("roomDeleteVis", "none");
		session.setAttribute("makeRequestVis", "none");
		session.setAttribute("currentUserRole", "guest");
		Locale locale = Util.defineLocale(request);
		session.setAttribute("locale", locale);

		request.getRequestDispatcher("main?action=getAllRooms").forward(request, response);
	}

}
