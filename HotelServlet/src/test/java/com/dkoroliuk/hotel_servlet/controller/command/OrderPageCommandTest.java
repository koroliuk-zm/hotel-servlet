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

import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk_hotel_servlet.model.DAO.OrderDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;

public class OrderPageCommandTest {
	@Test
	public void ExecuteShouldForwardToOrderPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getParameter("rId")).thenReturn("1");
		when(req.getRequestDispatcher("orderRoom.jsp")).thenReturn(reqDisp);
		try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
			roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
			try (MockedStatic<OrderDAOImpl> orderDAOMock = Mockito.mockStatic(OrderDAOImpl.class)) {
				orderDAOMock.when(() -> OrderDAOImpl.getInstance()).thenReturn(new OrderDAOMock());
				Command command = CommandFactory.getCommand("orderPage");
				command.execute(req, resp);
				verify(reqDisp).forward(req, resp);
			}
		}
	}
}
