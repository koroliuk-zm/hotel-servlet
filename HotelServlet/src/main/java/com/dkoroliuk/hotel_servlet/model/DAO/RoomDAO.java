package com.dkoroliuk.hotel_servlet.model.DAO;

import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;

public interface RoomDAO {

	List<RoomStatus> getAllRoomStatuses() throws DBException;

	List<RoomType> getAllRoomTypes() throws DBException;

	void addRoom(Room room) throws DBException;

	int roomsCount(String sort, String order, String seatsAmount, String perdayCost, String roomStatus, String roomType)
			throws DBException;

	List<Room> getAllRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount, String perdayCost,
			String roomStatus, String roomType) throws DBException;

	Room getRoomById(long id) throws DBException;

	void updateRoom(Room room) throws DBException;

	void deleteRoom(Room room) throws DBException;

	List<Room> getAllFreeRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount,
			String perdayCost, String roomStatus, String roomType) throws DBException;

}
