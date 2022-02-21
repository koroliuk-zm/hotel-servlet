package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command to process request of user by waiter {@link Request}s
 */
public class ProcessRequestCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		Long requestId = Long.valueOf(request.getParameter("rId"));
		Integer roomNumber = Integer.valueOf(request.getParameter("roomNumber"));
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateNumber(request, roomNumber, errors)) {
			Request req = requestDAO.getRequestById(requestId);
			request.setAttribute("request", req);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("main?action=processRequestPage&rId=" + requestId).forward(request, response);
			return;
		}

		requestDAO.processRequest(requestId, roomNumber);
		response.sendRedirect("main?action=waiterPage");
	}

}
