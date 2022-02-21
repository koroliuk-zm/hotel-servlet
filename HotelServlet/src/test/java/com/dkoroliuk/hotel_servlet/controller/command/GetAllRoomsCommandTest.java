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

import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOMock;
import com.dkoroliuk.hotel_servlet.util.Localizer;

public class GetAllRoomsCommandTest {
	@Test
	public void IfNotClientExecuteShouldForwardToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserRole")).thenReturn("waiter");
		when(req.getRequestDispatcher("rooms.jsp")).thenReturn(reqDisp);
		try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
			roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
			try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
				util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
				Command com = CommandFactory.getCommand("getAllRooms");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}
	
	@Test
	public void ExecuteShouldForwardToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserRole")).thenReturn("client");
		when(req.getRequestDispatcher("rooms.jsp")).thenReturn(reqDisp);
		try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
			roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
			try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
				util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
				Command com = CommandFactory.getCommand("getAllRooms");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}
}
