package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk_hotel_servlet.model.DAO.UserDAOMock;

public class UpdateUserCommandTest {
	
	@Test
	public void whenInvalidUserThenExecuteShouldForwardToUpdateUserPage() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse resp = mock(HttpServletResponse.class);
			RequestDispatcher reqDisp = mock(RequestDispatcher.class);
			when(req.getParameter("uId")).thenReturn("1");
			when(req.getParameter("login")).thenReturn("");
			when(req.getParameter("name")).thenReturn("");
			when(req.getParameter("surname")).thenReturn("");
			when(req.getParameter("roleId")).thenReturn("3");
			when(req.getParameter("email")).thenReturn("email@email.com");
			when(req.getParameter("isEnable")).thenReturn("");
			when(req.getParameter("password")).thenReturn("");
			when(req.getParameter("passwordConfirm")).thenReturn("");
			when(req.getRequestDispatcher("updateUser.jsp")).thenReturn(reqDisp);
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				Command com = CommandFactory.getCommand("updateUser");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}

	@Test
	public void whenValidUserThenExecuteShouldRedirectToAllUserPage() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				HttpServletRequest req = mock(HttpServletRequest.class);
				HttpServletResponse resp = mock(HttpServletResponse.class);
				when(req.getParameter("uId")).thenReturn("1");
				when(req.getParameter("login")).thenReturn("1");
				when(req.getParameter("name")).thenReturn("1");
				when(req.getParameter("surname")).thenReturn("1");
				when(req.getParameter("roleId")).thenReturn("3");
				when(req.getParameter("email")).thenReturn("email@email.com");
				when(req.getParameter("isEnable")).thenReturn("1");
				when(req.getParameter("password")).thenReturn("123456");
				when(req.getParameter("passwordConfirm")).thenReturn("123456");
				Command com = CommandFactory.getCommand("updateUser");
				com.execute(req, resp);
				
				verify(resp).sendRedirect("main?action=getAllUsers");
			}
		}
	}
}
