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
import com.dkoroliuk.hotel_servlet.util.Password;

public class LoginCommandTest {
	@Test
	public void whenWrongPasswordOrUserNullExecuteShouldForwardToLoginPage() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse resp = mock(HttpServletResponse.class);
			RequestDispatcher reqDisp = mock(RequestDispatcher.class);
			when(req.getParameter("login")).thenReturn("");
			when(req.getParameter("password")).thenReturn("");
			when(req.getRequestDispatcher("login.jsp")).thenReturn(reqDisp);
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				Command com = CommandFactory.getCommand("login");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}

	@Test
	public void whenPasswordAndUserCorrectExecuteShouldRedirectToDefault() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("login")).thenReturn("1");
		when(req.getParameter("password")).thenReturn("");
		try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
			userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
			try (MockedStatic<Password> pass = Mockito.mockStatic(Password.class)) {
				pass.when(() -> Password.isExpectedPassword(any(), any())).thenReturn(true);
				Command com = CommandFactory.getCommand("login");
				com.execute(req, resp);

				verify(resp).sendRedirect("main?action=default");
			}
		}
	}
}
