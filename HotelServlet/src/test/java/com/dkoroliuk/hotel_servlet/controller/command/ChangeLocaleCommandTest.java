package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

public class ChangeLocaleCommandTest {
	
	@Test
	public void ExecuteShouldForward() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("language")).thenReturn("uk");
		when(req.getParameter("country")).thenReturn("UA");
		when(req.getRequestDispatcher("main?action=default")).thenReturn(reqDisp);
		Command com = CommandFactory.getCommand("changeLocale");
		com.execute(req, resp);
		
		verify(reqDisp).forward(req, resp);
	}

}
