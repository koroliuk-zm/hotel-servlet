package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;
import com.dkoroliuk.hotel_servlet.util.Password;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command for updating {@link User}
 */
public class UpdateUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		Long uId = Long.valueOf(request.getParameter("uId"));
		User user = new User();
		user.setId(uId);
		user.setLogin(request.getParameter("login").trim());
		user.setName(request.getParameter("name").trim());
		user.setSurname(request.getParameter("surname").trim());
		UserRole userRole = new UserRole();
		userRole.setId(Integer.valueOf(request.getParameter("roleId")));
		user.setUserRole(userRole);
		user.setEmail(request.getParameter("email").trim());
		user.setEnable(Boolean.parseBoolean(request.getParameter("isEnable")));
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");

		UserDAO userDAO = UserDAOImpl.getInstance();
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateUser(request, user, errors)
				|| !Validator.validatePassword(request, password, passwordConfirm, errors)) {
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			request.setAttribute("userRoles", userDAO.getAllUserRoles());

			request.getRequestDispatcher("updateUser.jsp").forward(request, response);
			return;
		}

		user.setSalt(Password.generateSalt());
		user.setPassword(Password.hash(password, user.getSalt()));
		userDAO.updateUser(user);

		response.sendRedirect("main?action=getAllUsers");
	}
}
