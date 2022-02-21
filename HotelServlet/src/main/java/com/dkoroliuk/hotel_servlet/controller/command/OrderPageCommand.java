package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Room;

/**
 * Command to access order {@link Order}s page
 */
public class OrderPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		Long roomId = Long.valueOf(request.getParameter("rId"));
		Room room = roomDAO.getRoomById(roomId);
		request.setAttribute("room", room);

		request.getRequestDispatcher("orderRoom.jsp").forward(request, response);

	}

}
