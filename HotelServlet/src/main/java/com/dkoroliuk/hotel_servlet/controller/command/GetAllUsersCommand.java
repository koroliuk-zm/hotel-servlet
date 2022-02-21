package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;

/**
 * Command to access all {@link User}s page
 */
public class GetAllUsersCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		UserDAO userDAO = UserDAOImpl.getInstance();
		List<User> users = userDAO.getAllUsers();
		request.setAttribute("users", users);

		request.getRequestDispatcher("users.jsp").forward(request, response);

	}

}
