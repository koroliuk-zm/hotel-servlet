package com.dkoroliuk.hotel_servlet.controller.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class RegisterCommandTest {
	@Test
	public void ExecuteShouldForwardToRegisterPage() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher reqDisp = mock(RequestDispatcher.class);
		when(req.getRequestDispatcher("register.jsp")).thenReturn(reqDisp);
		Command addAuthor = CommandFactory.getCommand("register");
		addAuthor.execute(req, resp);

		verify(reqDisp).forward(req, resp);
	}
}
