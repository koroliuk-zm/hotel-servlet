package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOMock;
import com.dkoroliuk.hotel_servlet.util.Localizer;

public class AddUserCommandTest {
	@Test
	public void whenNotValidUserThenExecuteShouldForward() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse resp = mock(HttpServletResponse.class);
			RequestDispatcher reqDisp = mock(RequestDispatcher.class);
			when(req.getParameter("login")).thenReturn("");
			when(req.getParameter("name")).thenReturn("");
			when(req.getParameter("surname")).thenReturn("");
			when(req.getParameter("roleId")).thenReturn("3");
			when(req.getParameter("email")).thenReturn("");
			when(req.getParameter("password")).thenReturn("");
			when(req.getParameter("passwordConfirm")).thenReturn("");
			when(req.getRequestDispatcher("addUser.jsp")).thenReturn(reqDisp);
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				Command com = CommandFactory.getCommand("addUser");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}

	@Test
	public void whenValidUserThenExecuteShouldRedirect() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("login")).thenReturn("3");
		when(req.getParameter("name")).thenReturn("3");
		when(req.getParameter("surname")).thenReturn("3");
		when(req.getParameter("roleId")).thenReturn("3");
		when(req.getParameter("email")).thenReturn("email@email.com");
		when(req.getParameter("password")).thenReturn("1234567890");
		when(req.getParameter("passwordConfirm")).thenReturn("1234567890");
		try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
			userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
			Command com = CommandFactory.getCommand("addUser");
			com.execute(req, resp);

			verify(resp).sendRedirect("main?action=getAllUsers");
		}
	}
}
