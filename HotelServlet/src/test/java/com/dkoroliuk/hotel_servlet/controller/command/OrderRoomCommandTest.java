package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk_hotel_servlet.model.DAO.OrderDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.UserDAOMock;

public class OrderRoomCommandTest {

	@Test
	public void WhenOrderIsValidExecuteShouldRedirectToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("checkInDate")).thenReturn(LocalDate.now().toString());
		when(req.getParameter("checkOutDate")).thenReturn(LocalDate.now().toString());
		when(req.getParameter("rId")).thenReturn("1");
		when(req.getParameter("uId")).thenReturn("1");
		try (MockedStatic<OrderDAOImpl> orderDAOMock = Mockito.mockStatic(OrderDAOImpl.class)) {
			orderDAOMock.when(() -> OrderDAOImpl.getInstance()).thenReturn(new OrderDAOMock());
			try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
				roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
				try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
					userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
					Command com = CommandFactory.getCommand("orderRoom");
					com.execute(req, resp);
					verify(resp).sendRedirect("main?action=getAllRooms");
				}
			}
		}
	}
}