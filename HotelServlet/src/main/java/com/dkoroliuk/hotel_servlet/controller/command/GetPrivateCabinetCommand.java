package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.User;

/**
 * Command to access users private cabinet
 */
public class GetPrivateCabinetCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		UserDAO userDAO = UserDAOImpl.getInstance();
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("currentUserId");
		User currentUser = userDAO.getUserById(userId);
		request.setAttribute("currentUser", currentUser);

		OrderDAO orderDAO = OrderDAOImpl.getInstance();
		List<Order> unpaidOrders = orderDAO.getUserUnpaidOrders(userId);
		request.setAttribute("unpaidOrders", unpaidOrders);
		List<Order> ordersHistory = orderDAO.getUserOrdersHistory(userId);
		request.setAttribute("ordersHistory", ordersHistory);
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		List<Request> requests = requestDAO.getUserRequests(userId);
		request.setAttribute("userRequests", requests);

		request.getRequestDispatcher("privateCabinet.jsp").forward(request, response);
	}

}
