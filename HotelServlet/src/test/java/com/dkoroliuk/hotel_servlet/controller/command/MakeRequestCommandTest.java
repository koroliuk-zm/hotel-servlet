package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk_hotel_servlet.model.DAO.RequestDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.UserDAOMock;

public class MakeRequestCommandTest {
	@Test
	public void whenNotValidRequestThenExecuteShouldForward() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse resp = mock(HttpServletResponse.class);
			RequestDispatcher reqDisp = mock(RequestDispatcher.class);
			when(req.getParameter("seatsNumber")).thenReturn("-1");
			when(req.getParameter("checkInDate")).thenReturn(LocalDate.now().toString());
			when(req.getParameter("checkOutDate")).thenReturn(LocalDate.now().toString());
			when(req.getParameter("roomTypeId")).thenReturn("1");
			when(req.getParameter("uId")).thenReturn("1");
			when(req.getRequestDispatcher("request.jsp")).thenReturn(reqDisp);
			try (MockedStatic<RequestDAOImpl> requestDAOMock = Mockito.mockStatic(RequestDAOImpl.class)) {
				requestDAOMock.when(() -> RequestDAOImpl.getInstance()).thenReturn(new RequestDAOMock());
				try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
					roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
					try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
						userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
						Command com = CommandFactory.getCommand("makeRequest");
						com.execute(req, resp);
						verify(reqDisp).forward(req, resp);
					}
				}
			}
		}
	}

	@Test
	public void WhenRequestIsValidExecuteShouldRedirectToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		when(req.getParameter("seatsNumber")).thenReturn("1");
		when(req.getParameter("checkInDate")).thenReturn(LocalDate.now().toString());
		when(req.getParameter("checkOutDate")).thenReturn(LocalDate.now().toString());
		when(req.getParameter("roomTypeId")).thenReturn("1");
		when(req.getParameter("uId")).thenReturn("1");
		try (MockedStatic<RequestDAOImpl> requestDAOMock = Mockito.mockStatic(RequestDAOImpl.class)) {
			requestDAOMock.when(() -> RequestDAOImpl.getInstance()).thenReturn(new RequestDAOMock());
			try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
				userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
				Command com = CommandFactory.getCommand("makeRequest");
				com.execute(req, resp);
				verify(resp).sendRedirect("main?action=getAllRooms");
			}
		}
	}
}
