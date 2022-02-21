package com.dkoroliuk.hotel_servlet.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAOImpl;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.util.RoomComporators;
import com.dkoroliuk.hotel_servlet.util.RoomComporators.OrderBy;
import com.dkoroliuk.hotel_servlet.util.RoomComporators.SortBy;
import com.dkoroliuk.hotel_servlet.util.Util;

/**
 * Command to access all {@link Room}s page
 */
public class GetAllRoomsCommand implements Command {
	private static final int ROOMS_PER_PAGE = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBException {
		String sort = Util.defineSortAndStoreInCookie(request, response);
		request.setAttribute("sort", sort);

		String order = Util.defineOrderAndStoreInCookie(request, response);
		request.setAttribute("order", order);

		String seatsAmount = request.getParameter("seatsAmount");
		if (seatsAmount == null) {
			seatsAmount = "";
		}
		request.setAttribute("seatsAmount", seatsAmount);

		String perdayCost = request.getParameter("perdayCost");
		if (perdayCost == null) {
			perdayCost = "";
		}
		request.setAttribute("perdayCost", perdayCost);

		String roomStatus = request.getParameter("roomStatus");
		if (roomStatus == null) {
			roomStatus = "";
		}
		request.setAttribute("roomStatus", roomStatus);

		String roomType = request.getParameter("roomType");
		if (roomType == null) {
			roomType = "";
		}
		request.setAttribute("roomType", roomType);

		List<SortBy> sortBy = RoomComporators.getAllSortBy(request);
		request.setAttribute("sortBy", sortBy);

		List<OrderBy> orderBy = RoomComporators.getAllOrderBy(request);
		request.setAttribute("orderBy", orderBy);

		int page = 0;
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		RoomDAO roomDAO = RoomDAOImpl.getInstance();
		int roomsCount = roomDAO.roomsCount(sort, order, seatsAmount, perdayCost, roomStatus, roomType);
		int pagesCount = (int) Math.ceil(roomsCount / (double) ROOMS_PER_PAGE);
		String command = "main?action=getAllRooms";
		String paginationNav = Util.buildPaginationNavForRooms(command, page, pagesCount, seatsAmount, perdayCost,
				roomStatus, roomType);
		request.setAttribute("paginationNav", paginationNav);

		HttpSession session = request.getSession();
		String userRole = (String) session.getAttribute("currentUserRole");
		if (userRole.equals("client")) {
			List<Room> rooms = roomDAO.getAllFreeRooms(page, ROOMS_PER_PAGE, sort, order, seatsAmount, perdayCost,
					roomStatus, roomType);
			request.setAttribute("rooms", rooms);
		} else {
			List<Room> rooms = roomDAO.getAllRooms(page, ROOMS_PER_PAGE, sort, order, seatsAmount, perdayCost,
					roomStatus, roomType);
			request.setAttribute("rooms", rooms);
		}

		request.getRequestDispatcher("rooms.jsp").forward(request, response);

	}

}
