package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

public class DefaultCommandTest {
	
	@Test
	public void whenAdminThenExecuteShouldForwardToAdminPage () throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserRole")).thenReturn("admin");
		when(req.getRequestDispatcher("main?action=adminPage")).thenReturn(reqDisp);
		Command com = CommandFactory.getCommand("default");
		com.execute(req, resp);
		
		verify(reqDisp).forward(req, resp);
	}
	
	@Test
	public void whenWaiterThenExecuteShouldForwardToWaiterPage () throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserRole")).thenReturn("waiter");
		when(req.getRequestDispatcher("main?action=waiterPage")).thenReturn(reqDisp);
		Command com = CommandFactory.getCommand("default");
		com.execute(req, resp);
		
		verify(reqDisp).forward(req, resp);
	}
	
	@Test
	public void whenNotAdminOrWaiterThenExecuteShouldForwardToGetAllRooms () throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("currentUserRole")).thenReturn("");
		when(req.getRequestDispatcher("main?action=getAllRooms")).thenReturn(reqDisp);
		Command com = CommandFactory.getCommand("default");
		com.execute(req, resp);
		
		verify(reqDisp).forward(req, resp);
	}

}

