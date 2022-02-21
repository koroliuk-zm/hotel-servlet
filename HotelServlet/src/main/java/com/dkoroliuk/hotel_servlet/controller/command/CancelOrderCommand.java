package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;

public class CancelOrderCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		Long orderId = Long.valueOf(request.getParameter("oId"));
		OrderDAO orderDAO = OrderDAOImpl.getInstance();
		orderDAO.cancelOrder(orderId);

		response.sendRedirect("main?action=getPrivateCabinet");

	}

}
