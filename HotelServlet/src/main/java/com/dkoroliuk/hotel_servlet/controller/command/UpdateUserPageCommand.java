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
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;

/**
 * Command to access {@link User} update page
 */
public class UpdateUserPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		UserDAO userDAO = UserDAOImpl.getInstance();
		Long uId = Long.valueOf(request.getParameter("uId"));
		User user = userDAO.getUserById(uId);
		request.setAttribute("user", user);

		List<UserRole> userRoles = userDAO.getAllUserRoles();
		request.setAttribute("userRoles", userRoles);

		request.getRequestDispatcher("updateUser.jsp").forward(request, response);
	}

}
