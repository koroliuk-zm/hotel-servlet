package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk.hotel_servlet.util.Password;
import com.dkoroliuk.hotel_servlet.util.Util;

/**
 * Login command
 */
public class LoginCommand implements Command {
	private static final String INLINE_TABLE = "inline table";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		String login = request.getParameter("login");
		UserDAO userDao = UserDAOImpl.getInstance();
		User user = userDao.getUserByLogin(login);
		String password = request.getParameter("password");

		if (user == null || !Password.isExpectedPassword(Password.hash(password, user.getSalt()), user.getPassword())) {
			request.setAttribute("errorLoginMessage", Localizer.getString(request, "login.errormessage"));
			request.setAttribute("login", login);

			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		if ("admin".equals(user.getUserRole().getRole())) {
			session.setAttribute("roomEditVis", INLINE_TABLE);
			session.setAttribute("roomDeleteVis", INLINE_TABLE);
			session.setAttribute("roomOrderVis", "none");
			session.setAttribute("makeRequestVis", "none");
		}
		if ("client".equals(user.getUserRole().getRole())) {
			session.setAttribute("roomEditVis", "none");
			session.setAttribute("roomDeleteVis", "none");
			session.setAttribute("roomOrderVis", INLINE_TABLE);
			session.setAttribute("makeRequestVis", "button");
		}

		session.setAttribute("isEnable", user.isEnable());
		session.setAttribute("currentUserId", user.getId());
		session.setAttribute("currentUserRole", user.getUserRole().getRole());
		Locale locale = Util.defineLocale(request);
		session.setAttribute("locale", locale);

		response.sendRedirect("main?action=default");
	}

}
