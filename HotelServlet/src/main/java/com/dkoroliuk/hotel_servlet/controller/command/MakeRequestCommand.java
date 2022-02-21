package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command to make new {@link Request}s 
 */
public class MakeRequestCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException, ParseException {
		Request req = new Request();
		req.setRequestDate(LocalDateTime.now());
		req.setSeatsNumber(Integer.parseInt(request.getParameter("seatsNumber").trim()));
		req.setCheckInDate(LocalDate.parse(request.getParameter("checkInDate").trim()));
		req.setCheckOutDate(LocalDate.parse(request.getParameter("checkOutDate").trim()));
		RoomType roomType = new RoomType();
		roomType.setId(Integer.valueOf(request.getParameter("roomTypeId")));
		req.setRoomType(roomType);
		Long userId = Long.valueOf(request.getParameter("uId"));
		UserDAO userDAO = UserDAOImpl.getInstance();
		User user = userDAO.getUserById(userId);
		req.setUser(user);
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateRequest(request, req, errors)) {
			RoomDAO roomDAO = RoomDAOImpl.getInstance();
			List<RoomType> roomTypes = roomDAO.getAllRoomTypes();
			request.setAttribute("roomTypes", roomTypes);
			request.setAttribute("request", req);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("request.jsp").forward(request, response);
			return;
		}
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		requestDAO.addRequest(req, userId);
		response.sendRedirect("main?action=getAllRooms");

	}

}
