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
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.util.Localizer;
import com.dkoroliuk_hotel_servlet.model.DAO.RoomDAOMock;
import com.dkoroliuk_hotel_servlet.model.DAO.UserDAOMock;

public class GetAllUsersCommandTest {
	@Test
	public void ExecuteShouldForwardToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getRequestDispatcher("users.jsp")).thenReturn(reqDisp);
		try (MockedStatic<UserDAOImpl> userDAOMock = Mockito.mockStatic(UserDAOImpl.class)) {
			userDAOMock.when(() -> UserDAOImpl.getInstance()).thenReturn(new UserDAOMock());
			try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
				util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
				Command com = CommandFactory.getCommand("getAllUsers");
				com.execute(req, resp);

				verify(reqDisp).forward(req, resp);
			}
		}
	}
}
