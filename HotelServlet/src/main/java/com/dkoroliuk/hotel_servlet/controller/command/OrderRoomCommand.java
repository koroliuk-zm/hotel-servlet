package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.OrderDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command to order room {@link Order}s
 */
public class OrderRoomCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		Order order = new Order();
		order.setOrderDate(LocalDateTime.now());
		order.setCheckInDate(LocalDate.parse(request.getParameter("checkInDate").trim()));
		order.setCheckOutDate(LocalDate.parse(request.getParameter("checkOutDate").trim()));
		Long roomId = Long.valueOf(request.getParameter("rId"));
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		Room room = roomDAO.getRoomById(roomId);
		room.setId(roomId);
		order.setRoom(room);
		Long userId = Long.valueOf(request.getParameter("uId"));
		UserDAO userDAO = UserDAOImpl.getInstance();
		User user = userDAO.getUserById(userId);
		order.setUser(user);
		int totalCost = (int) ((ChronoUnit.DAYS.between(order.getCheckInDate(), order.getCheckOutDate()) + 1)
				* room.getPerdayCost());
		order.setTotalCost(totalCost);
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateOrder(request, order, errors)) {
			request.setAttribute("order", order);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("main?action=orderPage&rId=" + roomId).forward(request, response);
			return;
		}

		OrderDAO orderDAO = OrderDAOImpl.getInstance();
		orderDAO.addOrder(order, roomId, userId);
		response.sendRedirect("main?action=getAllRooms");
	}

}
