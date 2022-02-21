package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;

/**
 * Command to access update room {@link Room}s page
 */
public class UpdateRoomPageCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		long id = Long.parseLong(request.getParameter("rId"));
		Room room = roomDAO.getRoomById(id);
		request.setAttribute("room", room);
		List<RoomStatus> roomStatuses = roomDAO.getAllRoomStatuses();
		request.setAttribute("roomStatuses", roomStatuses);
		List<RoomType> roomTypes = roomDAO.getAllRoomTypes();
		request.setAttribute("roomTypes", roomTypes);

		request.getRequestDispatcher("updateRoom.jsp").forward(request, response);

	}

}
