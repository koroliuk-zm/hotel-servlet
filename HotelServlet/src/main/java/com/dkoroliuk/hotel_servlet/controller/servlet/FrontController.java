package com.dkoroliuk.hotel_servlet.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dkoroliuk.hotel_servlet.controller.command.Command;
import com.dkoroliuk.hotel_servlet.controller.command.CommandFactory;
import com.dkoroliuk.hotel_servlet.model.PooledConnections;

/**
 * Main controller.
 */
@WebServlet("/main")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger;

	static {
		logger = LogManager.getLogger(FrontController.class.getName());
	}

	@Override
	public void init() throws ServletException {
		PooledConnections.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Method to process request. Defines needed command through request parameter
	 * "action" and executes it.
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException in case of servlet exceptions
	 * @throws IOException      in case of io exceptions
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			Command command = CommandFactory.getCommand(action);
			command.execute(request, response);
		} catch (Exception e) {
			logger.error("Exception caught in FrontController", e);
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
