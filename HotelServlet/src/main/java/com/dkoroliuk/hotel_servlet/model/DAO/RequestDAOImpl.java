package com.dkoroliuk.hotel_servlet.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.PooledConnections;
import com.dkoroliuk.hotel_servlet.model.SqlQuery;
import com.dkoroliuk.hotel_servlet.model.entity.Request;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;
import com.dkoroliuk.hotel_servlet.model.entity.User;

public class RequestDAOImpl implements RequestDAO {
	private static RequestDAO instance;
	private static final Logger logger;

	static {
		logger = LogManager.getLogger(OrderDAOImpl.class.getName());
	}

	private RequestDAOImpl() {
	}

	public static synchronized RequestDAO getInstance() {
		if (instance == null) {
			instance = new RequestDAOImpl();
		}
		return instance;
	}

	@Override
	public void addRequest(Request request, Long userId) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.ADD_REQUEST)) {
			int k = 1;
			ps.setLong(k++, userId);
			ps.setLong(k++, request.getSeatsNumber());
			ps.setInt(k++, request.getRoomType().getId());
			ps.setObject(k++, request.getCheckInDate());
			ps.setObject(k++, request.getCheckOutDate());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can`t make request", e);
			throw new DBException("Can`t make request", e);
		}
	}

	@Override
	public List<Request> getUserRequests(Long userId) throws DBException {
		List<Request> requests = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement st = con.prepareStatement(SqlQuery.GET_USER_REQUESTS)) {
			st.setLong(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Request request = new Request();
					int k = 1;
					User user = new User();
					user.setId(rs.getLong(k++));
					user.setLogin(rs.getString(k++));
					user.setName(rs.getString(k++));
					user.setSurname(rs.getString(k++));
					request.setUser(user);
					request.setRequestDate(rs.getTimestamp(k++).toLocalDateTime());
					request.setCheckInDate(defineDate(rs.getTimestamp(k++)));
					request.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
					RoomType roomType = new RoomType();
					roomType.setId(rs.getInt(k++));
					roomType.setType(rs.getString(k++));
					request.setRoomType(roomType);
					requests.add(request);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get user`s requests", e);
			throw new DBException("Can`t get user`s requests", e);
		}
		return requests;
	}

	@Override
	public List<Request> getAllRequests() throws DBException {
		List<Request> requests = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement st = con.prepareStatement(SqlQuery.GET_ALL_REQUESTS)) {
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Request request = new Request();
					int k = 1;
					request.setId(rs.getLong(k++));
					User user = new User();
					user.setId(rs.getLong(k++));
					user.setLogin(rs.getString(k++));
					user.setName(rs.getString(k++));
					user.setSurname(rs.getString(k++));
					request.setUser(user);
					request.setRequestDate(rs.getTimestamp(k++).toLocalDateTime());
					request.setCheckInDate(defineDate(rs.getTimestamp(k++)));
					request.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
					RoomType roomType = new RoomType();
					roomType.setId(rs.getInt(k++));
					roomType.setType(rs.getString(k++));
					request.setRoomType(roomType);
					request.setSeatsNumber(rs.getInt(k++));
					requests.add(request);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get all requests", e);
			throw new DBException("Can`t get all requests", e);
		}
		return requests;
	}

	@Override
	public Request getRequestById(Long requestId) throws DBException {
		Request request = new Request();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.GET_REQUEST_BY_ID)) {
			ps.setLong(1, requestId);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				int k = 1;
				request.setId(rs.getLong(k++));
				request.setSeatsNumber(rs.getInt(k++));
				request.setRequestDate(rs.getTimestamp(k++).toLocalDateTime());
				request.setCheckInDate(defineDate(rs.getTimestamp(k++)));
				request.setCheckOutDate(defineDate(rs.getTimestamp(k++)));
				RoomType roomType = new RoomType();
				roomType.setId(rs.getInt(k++));
				roomType.setType(rs.getString(k++));
				request.setRoomType(roomType);
				User user = new User();
				user.setId(rs.getLong(k++));
				user.setLogin(rs.getString(k++));
				user.setName(rs.getString(k++));
				user.setSurname(rs.getString(k++));
				request.setUser(user);
			}
		} catch (SQLException e) {
			logger.error("Can`t get request by id", e);
			throw new DBException("Can`t request by id", e);
		}
		return request;
	}

	private LocalDate defineDate(Timestamp timestamp) {
		return (timestamp == null) ? null : timestamp.toLocalDateTime().toLocalDate();
	}

	@Override
	public void processRequest(Long requestId, Integer roomNumber) throws DBException {
		Connection con = null;
		try {
			con = PooledConnections.getInstance().getConnection();
			con.setAutoCommit(false);
			createOrderByRequest(con, requestId, roomNumber);
			changeRoomStatusToBooked(con, roomNumber);
			deleteRequest(con, requestId);
			con.commit();
		} catch (SQLException e) {
			rollback(con);
			logger.error("Can`t process request", e);
			throw new DBException("Can`t process request", e);
		} finally {
			close(con);
		}
	}

	private void deleteRequest(Connection con, Long requestId) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.DELETE_REQUEST)) {
			ps.setLong(1, requestId);
			ps.executeUpdate();
		}

	}

	private void changeRoomStatusToBooked(Connection con, Integer roomNumber) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.CHANGE_ROOM_STATUS_TO_BOOKED_BY_NUMBER);) {
			ps.setLong(1, roomNumber);
			ps.executeUpdate();
		}

	}

	private void createOrderByRequest(Connection con, Long requestId, Integer roomNumber) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(SqlQuery.CREATE_ORDER_BY_REQUEST)) {
			int k = 1;
			ps.setLong(k++, requestId);
			ps.setLong(k++, requestId);
			ps.setObject(k++, requestId);
			ps.setObject(k++, roomNumber);
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
				logger.warn("Can`t rollback transaction", e);
			}
		}
	}
}
