package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;

/**
 * Command to delete {@link User}
 */
public class DeleteUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		Long uId = Long.valueOf(request.getParameter("uId"));
		UserDAO userDAO = UserDAOImpl.getInstance();
		User user = userDAO.getUserById(uId);
		userDAO.deleteUser(user);
		
		response.sendRedirect("main?action=getAllUsers");
	}

}
