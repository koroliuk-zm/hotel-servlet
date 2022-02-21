package com.dkoroliuk.hotel_servlet.model.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RequestDAO;
import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.OrderStatus;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;
import com.dkoroliuk.hotel_servlet.model.entity.User;

public class RequestDAOMock implements RequestDAO {
	List<Request> requests;
	List<Order> orders;
	List<Room> rooms;
	
	public RequestDAOMock() {
		requests = new ArrayList<Request>();
		Request request = new Request();
		request.setId(1);
		request.setSeatsNumber(1);
		request.setRequestDate(LocalDateTime.now());
		request.setCheckInDate(LocalDate.now());
		request.setCheckOutDate(LocalDate.now());
		RoomType roomType = new RoomType();
		roomType.setId(1);
		roomType.setType("standart");
		request.setRoomType(roomType);
		User user = new User();
		user.setId(1L);
		request.setUser(user);
		requests.add(request);
		request = new Request();
		request.setId(2);
		request.setSeatsNumber(2);
		request.setRequestDate(LocalDateTime.now());
		request.setCheckInDate(LocalDate.now());
		request.setCheckOutDate(LocalDate.now());
		roomType = new RoomType();
		roomType.setId(2);
		roomType.setType("duplex");
		request.setRoomType(roomType);
		user = new User();
		user.setId(2L);
		request.setUser(user);
		requests.add(request);
		rooms = new ArrayList<Room>();
		Room room = new Room();
		room.setId(1);
		room.setNumber(1);
		room.setPerdayCost(1);
		room.setSeatsAmount(1);
		room.setDescription("desc");
		rooms.add(room);
	}
	@Override
	public void addRequest(Request request, Long userId) throws DBException {
		Request addedRequest = new Request();
		addedRequest.setCheckInDate(request.getCheckInDate());
		addedRequest.setCheckOutDate(request.getCheckOutDate());
		User user = new User();
		user.setId(userId);
		addedRequest.setUser(user);
		requests.add(addedRequest);
	}

	@Override
	public List<Request> getUserRequests(Long userId) throws DBException {
		return requests.stream().filter(request -> request.getUser().getId()==userId).toList();
	}

	@Override
	public List<Request> getAllRequests() throws DBException {
		return requests;
	}

	@Override
	public Request getRequestById(Long requestId) throws DBException {
		return requests.stream().filter(req->req.getId()==requestId).findAny().orElse(null);
	}

	@Override
	public void processRequest(Long requestId, Integer roomNumber) throws DBException {
		Request request = requests.stream().filter(req->req.getId()==requestId).findAny().orElse(null);
		User user = request.getUser(); 
		Order order = new Order();
		order.setOrderDate(LocalDateTime.now());
		order.setCheckInDate(request.getCheckInDate());
		order.setCheckOutDate(request.getCheckOutDate());
		order.setUser(user);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(2);
		order.setOrderStatus(orderStatus);
		Room room = rooms.stream().filter(r->r.getNumber()==roomNumber).findFirst().orElse(null);
		order.setRoom(room);
	}

}
