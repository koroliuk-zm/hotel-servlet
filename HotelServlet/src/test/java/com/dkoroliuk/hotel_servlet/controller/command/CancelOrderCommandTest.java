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

import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOMock;
import com.dkoroliuk.hotel_servlet.util.Localizer;

public class CancelOrderCommandTest {
	@Test
	public void ExecuteShouldRedirectToPrivateCabinet() throws Exception {
		try (MockedStatic<Localizer> util = Mockito.mockStatic(Localizer.class)) {
			util.when(() -> Localizer.getString(any(), any())).thenReturn("error");
			try (MockedStatic<OrderDAOImpl> orderDAOMock = Mockito.mockStatic(OrderDAOImpl.class)) {
				orderDAOMock.when(() -> OrderDAOImpl.getInstance()).thenReturn(new OrderDAOMock());
				HttpServletRequest req = mock(HttpServletRequest.class);
				HttpServletResponse resp = mock(HttpServletResponse.class);
				when(req.getParameter("oId")).thenReturn("1");
				Command com = CommandFactory.getCommand("cancelOrder");
				com.execute(req, resp);

				verify(resp).sendRedirect("main?action=getPrivateCabinet");
			}
		}
	}
}
