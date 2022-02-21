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
 * Command to access {@link User} adding page
 */
public class AddUserLinkCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		UserDAO roomDAO = UserDAOImpl.getInstance();
		List<UserRole> userRoles = roomDAO.getAllUserRoles();
		request.setAttribute("userRoles", userRoles);
		request.getRequestDispatcher("addUser.jsp").forward(request, response);
	}

}
