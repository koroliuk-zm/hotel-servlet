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
 * Command to update existed room {@link Room}s
 */
public class UpdateRoomCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		Long rId = Long.valueOf(request.getParameter("rId"));
		Room room = new Room();
		room.setId(rId);
		room.setNumber(Integer.parseInt(request.getParameter("number").trim()));
		room.setSeatsAmount(Integer.parseInt(request.getParameter("seatsAmount").trim()));
		room.setPerdayCost(Integer.parseInt(request.getParameter("perdayCost").trim()));
		RoomStatus roomStatus = new RoomStatus();
		roomStatus.setId(Integer.valueOf(request.getParameter("roomStatusId")));
		room.setRoomStatus(roomStatus);
		RoomType roomType = new RoomType();
		roomType.setId(Integer.valueOf(request.getParameter("roomTypeId")));
		room.setRoomType(roomType);
		room.setDescription(request.getParameter("description").trim());
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		HashMap<String, String> errors = new HashMap<>();
		if (!Validator.validateRoom(request, room, errors)) {
			request.setAttribute("errors", errors);
			request.setAttribute("room", room);
			request.setAttribute("roomStatuses", roomDAO.getAllRoomStatuses());
			request.setAttribute("roomTypes", roomDAO.getAllRoomTypes());
			request.getRequestDispatcher("updateRoom.jsp").forward(request, response);
			return;
		}
		roomDAO.updateRoom(room);

		response.sendRedirect("main?action=getAllRooms");

	}

}
