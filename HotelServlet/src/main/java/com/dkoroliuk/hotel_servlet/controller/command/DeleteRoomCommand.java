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
 * Command to delete {@link Room}
 */
public class DeleteRoomCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		Long rId = Long.valueOf(request.getParameter("rId"));
		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		Room room = roomDAO.getRoomById(rId);
		roomDAO.deleteRoom(room);
		
		response.sendRedirect("main?action=getAllRooms");	
	}

}
