package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

public class LogoutCommandTest {
	
	@Test
	public void ExecuteShouldForwardToAllRoomsPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		HttpSession sesion = mock(HttpSession.class);
		when(req.getSession()).thenReturn(sesion);
		when(req.getSession(true)).thenReturn(sesion);
		when(req.getRequestDispatcher("main?action=getAllRooms")).thenReturn(reqDisp);
		Command com = CommandFactory.getCommand("logout");
		com.execute(req, resp);
		
		verify(reqDisp).forward(req, resp);
	}

}
