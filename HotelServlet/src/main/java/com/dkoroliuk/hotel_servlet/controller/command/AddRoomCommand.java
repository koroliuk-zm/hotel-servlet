package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;
import com.dkoroliuk.hotel_servlet.util.Validator;

/**
 * Command for adding {@link Room}
 */
public class AddRoomCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		Room room = new Room();
		room.setNumber(Integer.valueOf(request.getParameter("number").trim()));
		room.setSeatsAmount(Integer.valueOf(request.getParameter("seatsAmount").trim()));
		room.setPerdayCost(Integer.valueOf(request.getParameter("perdayCost").trim()));
		RoomStatus roomStatus = new RoomStatus();
		roomStatus.setId(Integer.valueOf(request.getParameter("roomStatusId")));
		room.setRoomStatus(roomStatus);
		RoomType roomType = new RoomType();
		roomType.setId(Integer.valueOf(request.getParameter("roomTypeId")));
		room.setRoomType(roomType);
		room.setDescription(request.getParameter("description"));
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateRoom(request, room, errors)) {
			request.setAttribute("room", room);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("addRoom.jsp").forward(request, response);
			return;
		}

		RoomDAO bookDAO = RoomDAOImpl.getInstance();
		bookDAO.addRoom(room);

		response.sendRedirect("main?action=getAllRooms");

	}

}
