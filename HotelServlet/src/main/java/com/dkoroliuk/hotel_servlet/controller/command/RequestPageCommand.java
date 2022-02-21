package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;

/**
 * Command to access request {@link Request}s page
 */
public class RequestPageCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		List<RoomType> roomTypes = roomDAO.getAllRoomTypes();
		request.setAttribute("roomTypes", roomTypes);
		request.getRequestDispatcher("request.jsp").forward(request, response);

	}
}
