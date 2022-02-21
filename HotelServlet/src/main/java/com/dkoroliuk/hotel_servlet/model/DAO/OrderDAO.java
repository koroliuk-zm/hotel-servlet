package com.dkoroliuk.hotel_servlet.model.DAO;

import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.entity.Order;

public interface OrderDAO {

	void addOrder(Order order, Long roomId, Long userId) throws DBException;

	List<Order> getUserUnpaidOrders(Long userId) throws DBException;

	List<Order> getUserOrdersHistory(Long userId) throws DBException;

	void payOrder(Long orderId) throws DBException;

	void cancelOrder(Long orderId) throws DBException;

	List<Order> getAllOrders();

	void setExpiredIfOrderUnpaidMoreThanTwoDays(Order order);

}
