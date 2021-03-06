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

import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOMock;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOMock;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOMock;
import com.dkoroliuk.hotel_servlet.util.Localizer;

public class WaiterPageCommandTest {
	@Test
	public void executeShouldForwardToWaiterPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserId")).thenReturn(1L);
		when(req.getRequestDispatcher("waiterCabinet.jsp")).thenReturn(reqDisp);
		try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
			userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
			try (MockedStatic<RoomDAOImpl> roomDAOMock = Mockito.mockStatic(RoomDAOImpl.class)) {
				roomDAOMock.when(() -> RoomDAOImpl.getInstance()).thenReturn(new RoomDAOMock());
				try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
					util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
					try (MockedStatic<RequestDAOImpl> requestDAOMock = Mockito.mockStatic(RequestDAOImpl.class)) {
						requestDAOMock.when(() -> RequestDAOImpl.getInstance()).thenReturn(new RequestDAOMock());
						Command com = CommandFactory.getCommand("waiterPage");
						com.execute(req, resp);

						verify(reqDisp).forward(req, resp);
					}
				}
			}
		}
	}
}
