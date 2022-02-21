package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Default command
 */
public class DefaultCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String currentUserRole = (String) session.getAttribute("currentUserRole");
		String page = "main?action=getAllRooms";
		if ("admin".equals(currentUserRole)) {
			page = "main?action=adminPage";
		}
		if ("waiter".equals(currentUserRole)) {
			page = "main?action=waiterPage";
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

}
