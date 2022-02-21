package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;

/**
 * Command to access admin page
 */
public class AdminPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		UserDAO userDAO = UserDAOImpl.getInstance();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("currentUserId");
		User currentUser = userDAO.getUserById(userId);
		request.setAttribute("currentUser", currentUser);

		request.getRequestDispatcher("admin.jsp").forward(request, response);

	}

}
