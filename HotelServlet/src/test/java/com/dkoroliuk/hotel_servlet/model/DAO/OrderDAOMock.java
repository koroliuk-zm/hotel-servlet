package com.dkoroliuk.hotel_servlet.model.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.OrderStatus;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.User;

public class OrderDAOMock implements OrderDAO {
	List<Order> orders;
	List<OrderStatus> orderStatuses;

	public OrderDAOMock() {
		orderStatuses = new ArrayList<OrderStatus>();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(1);
		orderStatus.setStatus("new");
		orderStatuses.add(orderStatus);
		orderStatus = new OrderStatus();
		orderStatus.setId(2);
		orderStatus.setStatus("confirmed");
		orderStatuses.add(orderStatus);
		orders = new ArrayList<Order>();
		Order order = new Order();
		order.setId(1);
		order.setTotalCost(1);
		order.setCheckInDate(LocalDate.now());
		order.setCheckOutDate(LocalDate.now());
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus(orderStatuses.get(0));
		Room room = new Room();
		room.setId(1);
		RoomStatus rs = new RoomStatus();
		rs.setId(1);
		rs.setStatus("free");
		room.setRoomStatus(rs);
		order.setRoom(room);
		User user = new User();
		user.setId(1);
		order.setUser(user);
		orders.add(order);
		order = new Order();
		order.setId(2);
		order.setTotalCost(2);
		order.setCheckInDate(LocalDate.now());
		order.setCheckOutDate(LocalDate.now());
		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus(orderStatuses.get(1));
		room = new Room();
		room.setId(2);
		room.setRoomStatus(rs);
		order.setRoom(room);
		user = new User();
		user.setId(2);
		order.setUser(user);
		orders.add(order);
	}

	@Override
	public void addOrder(Order order, Long roomId, Long userId) throws DBException {
		Order addedOrder = new Order();
		addedOrder.setCheckInDate(order.getCheckInDate());
		addedOrder.setCheckOutDate(order.getCheckOutDate());
		User user = new User();
		user.setId(userId);
		addedOrder.setUser(user);
		Room room = new Room();
		room.setId(roomId);
		addedOrder.setRoom(room);
		orders.add(addedOrder);
	}

	@Override
	public List<Order> getUserUnpaidOrders(Long userId) throws DBException {
		return orders.stream()
				.filter(order -> order.getUser().getId() == userId)
				.filter(order -> order.getOrderStatus().getId() < 3)
				.toList();
	}

	@Override
	public List<Order> getUserOrdersHistory(Long userId) throws DBException {
		return orders.stream()
				.filter(order -> order.getUser().getId() == userId)
				.filter(order -> order.getOrderStatus().getId() > 2)
				.toList();
	}

	@Override
	public void payOrder(Long orderId) throws DBException {
		Order order = orders.stream().filter(o->o.getId()==orderId).findAny().orElse(null);
		OrderStatus orderStatus = order.getOrderStatus();
		orderStatus.setId(3);
		order.setOrderStatus(orderStatus);
		Room room = order.getRoom();
		RoomStatus roomStatus = room.getRoomStatus();
		roomStatus.setId(3);
		room.setRoomStatus(roomStatus);
		order.setRoom(room);
	}

	@Override
	public void cancelOrder(Long orderId) throws DBException {
		Order order = orders.stream().filter(o->o.getId()==orderId).findAny().orElse(null);
		Room room = order.getRoom();
		RoomStatus roomStatus = room.getRoomStatus();
		roomStatus.setId(1);
		room.setRoomStatus(roomStatus);
		order.setRoom(room);
		orders.remove(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orders;
	}

	@Override
	public void setExpiredIfOrderUnpaidMoreThanTwoDays(Order order) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setClosedAndFeeRoom(Order order) {
		// TODO Auto-generated method stub
		
	}

}
