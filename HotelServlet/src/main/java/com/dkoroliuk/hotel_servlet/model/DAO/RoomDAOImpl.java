package com.dkoroliuk.hotel_servlet.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.PooledConnections;
import com.dkoroliuk.hotel_servlet.model.SqlQuery;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;

public class RoomDAOImpl implements RoomDAO {
	private static RoomDAO instance;
	private static final Logger logger;

	static {
		logger = LogManager.getLogger(RoomDAOImpl.class.getName());
	}

	private RoomDAOImpl() {
	}

	public static synchronized RoomDAO getInstance() {
		if (instance == null) {
			instance = new RoomDAOImpl();
		}
		return instance;
	}

	@Override
	public List<RoomStatus> getAllRoomStatuses() throws DBException {
		List<RoomStatus> roomStatuses = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery.GET_ALL_ROOMSTATUSES)) {
			while (rs.next()) {
				RoomStatus roomStatus = new RoomStatus();
				int k = 1;
				roomStatus.setId(rs.getInt(k++));
				roomStatus.setStatus(rs.getString(k++));
				roomStatuses.add(roomStatus);
			}
		} catch (SQLException e) {
			logger.error("Can`t get all room statuses", e);
			throw new DBException("Can`t get all room statuses", e);
		}
		return roomStatuses;
	}

	@Override
	public List<RoomType> getAllRoomTypes() throws DBException {
		List<RoomType> roomTypes = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery.GET_ALL_ROOMTYPES)) {
			while (rs.next()) {
				RoomType roomType = new RoomType();
				int k = 1;
				roomType.setId(rs.getInt(k++));
				roomType.setType(rs.getString(k++));
				roomTypes.add(roomType);
			}
		} catch (SQLException e) {
			logger.error("Can`t get all room types", e);
			throw new DBException("Can`t get all room types", e);
		}
		return roomTypes;
	}

	@Override
	public void addRoom(Room room) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.ADD_ROOM,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			ps.setInt(k++, room.getNumber());
			ps.setInt(k++, room.getSeatsAmount());
			ps.setInt(k++, room.getPerdayCost());
			ps.setInt(k++, room.getRoomStatus().getId());
			ps.setInt(k++, room.getRoomType().getId());
			ps.setString(k++, room.getDescription());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				room.setId(rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Can`t create new room", e);
			throw new DBException("Can`t create new room", e);
		}

	}

	@Override
	public int roomsCount(String sort, String order, String seatsAmount, String perdayCost, String roomStatus,
			String roomType) throws DBException {
		int result = 10;
		String query = "SELECT COUNT(*) AS count " + "FROM room r "
				+ "INNER JOIN room_status rs ON r.room_statuses_id = rs.id "
				+ "INNER JOIN room_type rt ON r.room_types_id = rt.id "
				+ "WHERE r.seats_amount LIKE( ? ) AND r.perday_cost LIKE( ? ) AND rs.status LIKE( ? ) AND rt.type LIKE( ? ) "
				+ "ORDER BY ";
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(query + sort + " " + order)) {
			int k = 1;
			ps.setString(k++, "%" + seatsAmount + "%");
			ps.setString(k++, "%" + perdayCost + "%");
			ps.setString(k++, "%" + roomStatus + "%");
			ps.setString(k++, "%" + roomType + "%");
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = rs.getInt("count");
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get rooms count", e);
			throw new DBException("Can`t get rooms count", e);
		}
		return result;
	}

	@Override
	public List<Room> getAllRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount,
			String perdayCost, String roomStatus, String roomType) throws DBException {
		String query = "SELECT r.id AS rid, r.number, r.seats_amount, r.perday_cost, rs.id AS rsid, "
				+ "rs.status, rt.id AS rtid, rt.type, r.description "
				+ "FROM room r INNER JOIN room_status rs ON r.room_statuses_id = rs.id "
				+ "INNER JOIN room_type rt ON r.room_types_id = rt.id "
				+ "WHERE r.seats_amount LIKE( ? ) AND r.perday_cost LIKE( ? ) AND rs.status LIKE( ? ) AND rt.type LIKE (?) "
				+ "ORDER BY ";
		query += sort + " " + order + " " + "LIMIT " + roomsPerPage + " " + " OFFSET " + page * roomsPerPage;
		List<Room> rooms = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			int j = 1;
			ps.setString(j++, "%" + seatsAmount + "%");
			ps.setString(j++, "%" + perdayCost + "%");
			ps.setString(j++, "%" + roomStatus + "%");
			ps.setString(j++, "%" + roomType + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Room room = new Room();
					int k = 1;
					room.setId(rs.getLong(k++));
					room.setNumber(rs.getInt(k++));
					room.setSeatsAmount(rs.getInt(k++));
					room.setPerdayCost(rs.getInt(k++));
					RoomStatus roomSt = new RoomStatus();
					roomSt.setId(rs.getInt(k++));
					roomSt.setStatus(rs.getString(k++));
					room.setRoomStatus(roomSt);
					RoomType rt = new RoomType();
					rt.setId(rs.getInt(k++));
					rt.setType(rs.getString(k++));
					room.setRoomType(rt);
					room.setDescription(rs.getString(k++));
					rooms.add(room);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get all rooms", e);
			throw new DBException("Can`t get all rooms", e);
		}
		return rooms;
	}

	@Override
	public Room getRoomById(long id) throws DBException {
		Room room = new Room();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.GET_ROOM_BY_ID)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				int k = 1;
				room.setId(rs.getLong(k++));
				room.setNumber(rs.getInt(k++));
				room.setSeatsAmount(rs.getInt(k++));
				room.setPerdayCost(rs.getInt(k++));
				RoomStatus roomStatus = new RoomStatus();
				roomStatus.setId(rs.getInt(k++));
				roomStatus.setStatus(rs.getString(k++));
				room.setRoomStatus(roomStatus);
				RoomType roomType = new RoomType();
				roomType.setId(rs.getInt(k++));
				roomType.setType(rs.getString(k++));
				room.setRoomType(roomType);
				room.setDescription(rs.getString(k++));
			}
		} catch (SQLException e) {
			logger.error("Can`t get room by id", e);
			throw new DBException("Can`t get room by id", e);
		}
		return room;
	}

	@Override
	public void updateRoom(Room room) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.UPDATE_ROOM)) {
			int k = 1;
			ps.setInt(k++, room.getNumber());
			ps.setInt(k++, room.getSeatsAmount());
			ps.setInt(k++, room.getPerdayCost());
			ps.setInt(k++, room.getRoomStatus().getId());
			ps.setInt(k++, room.getRoomType().getId());
			ps.setString(k++, room.getDescription());
			ps.setLong(k++, room.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can`t update room", e);
			throw new DBException("Can`t update room", e);
		}

	}

	@Override
	public void deleteRoom(Room room) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.DELETE_ROOM)) {
			int k = 1;
			ps.setLong(k++, room.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can`t delete room", e);
			throw new DBException("Can`t delete room", e);
		}

	}

	@Override
	public List<Room> getAllFreeRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount,
			String perdayCost, String roomStatus, String roomType) throws DBException {
		String query = "SELECT r.id AS rid, r.number, r.seats_amount, r.perday_cost, rs.id AS rsid, "
				+ "rs.status, rt.id AS rtid, rt.type, r.description "
				+ "FROM room r INNER JOIN room_status rs ON r.room_statuses_id = rs.id "
				+ "INNER JOIN room_type rt ON r.room_types_id = rt.id "
				+ "WHERE r.room_statuses_id = 1 AND r.seats_amount LIKE( ? ) AND r.perday_cost LIKE( ? ) AND rs.status LIKE( ? ) AND rt.type LIKE (?) "
				+ "ORDER BY ";
		query += sort + " " + order + " " + "LIMIT " + roomsPerPage + " " + " OFFSET " + page * roomsPerPage;
		List<Room> rooms = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			int j = 1;
			ps.setString(j++, "%" + seatsAmount + "%");
			ps.setString(j++, "%" + perdayCost + "%");
			ps.setString(j++, "%" + roomStatus + "%");
			ps.setString(j++, "%" + roomType + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Room room = new Room();
					int k = 1;
					room.setId(rs.getLong(k++));
					room.setNumber(rs.getInt(k++));
					room.setSeatsAmount(rs.getInt(k++));
					room.setPerdayCost(rs.getInt(k++));
					RoomStatus roomSt = new RoomStatus();
					roomSt.setId(rs.getInt(k++));
					roomSt.setStatus(rs.getString(k++));
					room.setRoomStatus(roomSt);
					RoomType rt = new RoomType();
					rt.setId(rs.getInt(k++));
					rt.setType(rs.getString(k++));
					room.setRoomType(rt);
					room.setDescription(rs.getString(k++));
					rooms.add(room);
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get all free rooms", e);
			throw new DBException("Can`t get all free rooms", e);
		}
		return rooms;
	}
}
