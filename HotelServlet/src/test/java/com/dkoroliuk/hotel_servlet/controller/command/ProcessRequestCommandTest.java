package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk_hotel_servlet.model.DAO.RequestDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.UserDAOMock;

public class ProcessRequestCommandTest {

	@Test
	public void WhenIsValidExecuteShouldRedirectToWaiterPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("rId")).thenReturn("1");
		when(req.getParameter("roomNumber")).thenReturn("1");
		try (MockedStatic<RequestDAOImpl> requestDAOMock = Mockito.mockStatic(RequestDAOImpl.class)) {
			requestDAOMock.when(() -> RequestDAOImpl.getInstance()).thenReturn(new RequestDAOMock());
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				Command com = CommandFactory.getCommand("processRequest");
				com.execute(req, resp);
				verify(resp).sendRedirect("main?action=waiterPage");
			}
		}
	}
}
