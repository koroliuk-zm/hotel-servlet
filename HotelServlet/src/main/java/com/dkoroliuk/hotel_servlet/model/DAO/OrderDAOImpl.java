package com.dkoroliuk.hotel_servlet.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.PooledConnections;
import com.dkoroliuk.hotel_servlet.model.SqlQuery;
import com.dkoroliuk.hotel_servlet.model.entity.Order;
import com.dkoroliuk.hotel_servlet.model.entity.OrderStatus;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.User;

public class OrderDAOImpl implements OrderDAO {
	private static OrderDAO instance;
	private static final Logger logger;

	static {
		logger = LogManager.getLogger(OrderDAOImpl.class.getName());
	}

	private OrderDAOImpl() {
	}

	public static synchronized OrderDAO getInstance() {
		if (instance == null) {
			instance = new OrderDAOImpl();
		}
		return instance;
	}

	@Override
	public void addOrder(Order order, Long roomId, Long userId) throws DBException {
		Connection con = null;
		try {
			con = PooledConnections.getInstance().getConnection();
			con.setAutoCommit(false);
			insertRoomOrder(con, order, roomId, userId);
			changeRoomStatusToBooked(con, roomId);
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			logger.error("Can`t create new room order", e);
			throw new DBException("Can`t create new room order", e);
		} finally {
			close(con);
		}
	}

	private void insertRoomOrder(Connection con, Order order, Long roomId, Long userId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.ADD_ROOM_ORDER)) {
			int k = 1;
			ps.setLong(k++, userId);
			ps.setLong(k++, roomId);
			ps.setObject(k++, order.getCheckInDate());
			ps.setObject(k++, order.getCheckOutDate());
			ps.executeUpdate();
		}

	}

	private void changeRoomStatusToBooked(Connection con, Long roomId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.CHANGE_ROOM_STATUS_TO_BOOKED);) {
			ps.setLong(1, roomId);
			ps.executeUpdate();
		}

	}

	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				logger.warn("Can`t close connection", e);
			}
		}
	}

	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				logger.warn("Can`t roolback transaction", e);
			}
		}
	}

	@Override
	public List<Order> getUserUnpaidOrders(Long userId) throws DBException {
		List<Order> unpaidOrders = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement st = con.prepareStatement(SqlQuery.GET_UNPAID_USER_ORDERS)) {
			st.setLong(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Order order = new Order();
					int k = 1;
					order.setId(rs.getLong(k++));
					User user = new User();
					user.setId(rs.getLong(k++));
					user.setLogin(rs.getString(k++));
					user.setName(rs.getString(k++));
					user.setSurname(rs.getString(k++));
					order.setUser(user);
					Room room = new Room();
					room.setId(rs.getLong(k++));
					room.setNumber(rs.getInt(k++));
					order.setRoom(room);
					order.setOrderDate(rs.getTimestamp(k++).toLocalDateTime());
					order.setCheckInDate(defineDate(rs.getTimestamp(k++)));
					order.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
					order.setTotalCost(rs.getInt(k++));
					OrderStatus orderStatus = new OrderStatus();
					orderStatus.setId(rs.getInt(k++));
					orderStatus.setStatus(rs.getString(k++));
					order.setOrderStatus(orderStatus);
					unpaidOrders.add(order);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get user`s unpaid orders", e);
			throw new DBException("Can`t get user`s unpaid orders", e);
		}
		return unpaidOrders;
	}

	private LocalDate defineDate(Timestamp timestamp) {
		return (timestamp == null) ? null : timestamp.toLocalDateTime().toLocalDate();
	}

	@Override
	public List<Order> getUserOrdersHistory(Long userId) throws DBException {
		List<Order> ordersHistory = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement st = con.prepareStatement(SqlQuery.GET_USER_HISTORY)) {
			st.setLong(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Order order = new Order();
					int k = 1;
					order.setId(rs.getLong(k++));
					User user = new User();
					user.setId(rs.getLong(k++));
					user.setLogin(rs.getString(k++));
					user.setName(rs.getString(k++));
					user.setSurname(rs.getString(k++));
					order.setUser(user);
					Room room = new Room();
					room.setId(rs.getLong(k++));
					room.setNumber(rs.getInt(k++));
					order.setRoom(room);
					order.setOrderDate(rs.getTimestamp(k++).toLocalDateTime());
					order.setCheckInDate(defineDate(rs.getTimestamp(k++)));
					order.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
					order.setTotalCost(rs.getInt(k++));
					OrderStatus orderStatus = new OrderStatus();
					orderStatus.setId(rs.getInt(k++));
					orderStatus.setStatus(rs.getString(k++));
					order.setOrderStatus(orderStatus);
					ordersHistory.add(order);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get user`s orders history", e);
			throw new DBException("Can`t get user`s orders history", e);
		}
		return ordersHistory;
	}

	@Override
	public void payOrder(Long orderId) throws DBException {
		Connection con = null;
		try {
			con = PooledConnections.getInstance().getConnection();
			con.setAutoCommit(false);
			setOrderStatusPaid(con, orderId);
			changeRoomStatusToBusy(con, orderId);
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			logger.error("Can`t pay order", e);
			throw new DBException ("Can`t pay order", e);
		} finally {
			close(con);
		}
	}

	private void changeRoomStatusToBusy(Connection con, Long orderId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.SET_ROOM_STATUS_BUSY);) {
			ps.setLong(1, orderId);
			ps.executeUpdate();
		}

	}

	private void setOrderStatusPaid(Connection con, Long orderId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.SET_ORDER_STATUS_PAID);) {
			ps.setLong(1, orderId);
			ps.executeUpdate();
		}
	}

	@Override
	public void cancelOrder(Long orderId) throws DBException {
		Connection con = null;
		try {
			con = PooledConnections.getInstance().getConnection();
			con.setAutoCommit(false);
			changeRoomStatusToFree(con, orderId);
			deleteOrder(con, orderId);
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			logger.error("Can`t cancel order", e);
			throw new DBException("Can`t cancel order", e);
		} finally {
			close(con);
		}
	}

	private void deleteOrder(Connection con, Long orderId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.DELETE_ORDER)) {
			ps.setLong(1, orderId);
			ps.executeUpdate();
		}
	}

	private void changeRoomStatusToFree(Connection con, Long orderId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.SET_ROOM_STATUS_FREE)) {
			ps.setLong(1, orderId);
			ps.executeUpdate();
		}
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery.GET_ALL_ORDERS)) {
			while (rs.next()) {
				Order order = new Order();
				int k = 1;
				order.setId(rs.getLong(k++));
				User user = new User();
				user.setId(rs.getLong(k++));
				user.setLogin(rs.getString(k++));
				user.setName(rs.getString(k++));
				user.setSurname(rs.getString(k++));
				order.setUser(user);
				Room room = new Room();
				room.setId(rs.getLong(k++));
				room.setNumber(rs.getInt(k++));
				order.setRoom(room);
				order.setOrderDate(rs.getTimestamp(k++).toLocalDateTime());
				order.setCheckInDate(defineDate(rs.getTimestamp(k++)));
				order.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
				order.setTotalCost(rs.getInt(k++));
				OrderStatus orderStatus = new OrderStatus();
				orderStatus.setId(rs.getInt(k++));
				orderStatus.setStatus(rs.getString(k++));
				order.setOrderStatus(orderStatus);
				orders.add(order);
			}
		} catch (SQLException e) {
			logger.error("Can`t get all orders", e);
		}
		return orders;
	}

	@Override
	public void setExpiredIfOrderUnpaidMoreThanTwoDays(Order order) {
		Connection con = null;
		try {
			con = PooledConnections.getInstance().getConnection();
			con.setAutoCommit(false);
			changeRoomStatusToFree(con, order.getRoom().getId());
			changeOrderStatusToExpired(con, order.getId());
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			logger.error("Can`t set order expired when order is not paid more than two days", e);
		} finally {
			close(con);
		}

	}

	private void changeOrderStatusToExpired(Connection con, long orderId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.SET_ORDER_STATUS_EXPIRED);) {
			ps.setLong(1, orderId);
			ps.executeUpdate();
		}
	}

}
