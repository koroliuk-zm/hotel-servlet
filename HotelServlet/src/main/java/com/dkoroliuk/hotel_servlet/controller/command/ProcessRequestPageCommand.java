package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Request;

/**
 * Command to access process request {@link Request}s page
 */
public class ProcessRequestPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		Long requestId = Long.valueOf(request.getParameter("rId"));
		Request req = requestDAO.getRequestById(requestId);
		request.setAttribute("request", req);
		request.getRequestDispatcher("processRequest.jsp").forward(request, response);
	}

}
