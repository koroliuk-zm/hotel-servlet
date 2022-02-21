package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;
import com.dkoroliuk.hotel_servlet.util.Password;
import com.dkoroliuk.hotel_servlet.util.Util;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command for register new {@link User}
 */
public class RegisterUserCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		User user = new User();
		user.setLogin(request.getParameter("login"));
		user.setName(request.getParameter("name"));
		user.setSurname(request.getParameter("surname"));
		user.setEmail(request.getParameter("email"));
		UserRole userRole = new UserRole();
		userRole.setId(3);
		userRole.setRole("client");
		user.setUserRole(userRole);
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");

		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateUser(request, user, errors)
				|| !Validator.validatePassword(request, password, passwordConfirm, errors)) {
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);

			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		user.setSalt(Password.generateSalt());
		user.setPassword(Password.hash(password, user.getSalt()));
		UserDAO userDAO = UserDAOImpl.getInstance();
		userDAO.registerUser(user);

		HttpSession session = request.getSession();
		session.setAttribute("currentUserId", user.getId());
		session.setAttribute("currentUserRole", user.getUserRole().getRole());
		Locale locale = Util.defineLocale(request);
		session.setAttribute("locale", locale);

		response.sendRedirect("login.jsp");
	}

}
