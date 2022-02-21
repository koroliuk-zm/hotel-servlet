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

import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;

public class UpdateRoomCommandTest {
	@Test
	public void whenNotValidRoomThenExecuteShouldForwardToUpdateRoomPage() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse resp = mock(HttpServletResponse.class);
			RequestDispatcher reqDisp = mock(RequestDispatcher.class);
			when(req.getParameter("rId")).thenReturn("1");
			when(req.getParameter("number")).thenReturn("1");
			when(req.getParameter("seatsAmount")).thenReturn("1");
			when(req.getParameter("perdayCost")).thenReturn("1");
			when(req.getParameter("roomStatusId")).thenReturn("1");
			when(req.getParameter("roomTypeId")).thenReturn("1");
			when(req.getParameter("description")).thenReturn("");
			when(req.getRequestDispatcher("updateRoom.jsp")).thenReturn(reqDisp);
			try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
				roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
				Command com = CommandFactory.getCommand("updateRoom");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}

	@Test
	public void whenValidRoomThenShouldRedirectToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("rId")).thenReturn("1");
		when(req.getParameter("number")).thenReturn("1");
		when(req.getParameter("seatsAmount")).thenReturn("1");
		when(req.getParameter("perdayCost")).thenReturn("1");
		when(req.getParameter("roomStatusId")).thenReturn("1");
		when(req.getParameter("roomTypeId")).thenReturn("1");
		when(req.getParameter("description")).thenReturn("desc");
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
				roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
				Command com = CommandFactory.getCommand("updateRoom");
				com.execute(req, resp);
				verify(resp).sendRedirect("main?action=getAllRooms");
			}
		}
	}
}
