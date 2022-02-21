package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOMock;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOMock;

public class RequestPageCommandTest {
	@Test
	public void ExecuteShouldForwardToRequestPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getRequestDispatcher("request.jsp")).thenReturn(reqDisp);
		try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
			roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
			try (MockedStatic<RequestDAOImpl> requestDAOMock = Mockito.mockStatic(RequestDAOImpl.class)) {
				requestDAOMock.when(() -> RequestDAOImpl.getInstance()).thenReturn(new RequestDAOMock());
				Command command = CommandFactory.getCommand("requestPage");
				command.execute(req, resp);
				verify(reqDisp).forward(req, resp);
			}
		}
	}
}
