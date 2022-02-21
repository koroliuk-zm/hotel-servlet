package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;

public class DeleteRoomCommandTest {
	@Test
	public void ExecuteShouldRedirectToAllRoomsPage() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
				roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
				HttpServletRequest req = mock(HttpServletRequest.class);
				HttpServletResponse resp = mock(HttpServletResponse.class);
				when(req.getParameter("rId")).thenReturn("1");
				Command com = CommandFactory.getCommand("deleteRoom");
				com.execute(req, resp);

				verify(resp).sendRedirect("main?action=getAllRooms");
			}
		}
	}
}
