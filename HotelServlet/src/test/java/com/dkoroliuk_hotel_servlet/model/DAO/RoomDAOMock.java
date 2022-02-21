package com.dkoroliuk_hotel_servlet.model.DAO;

import java.util.ArrayList;
import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.RoomDAO;
import com.dkoroliuk.hotel_servlet.model.entity.Room;
import com.dkoroliuk.hotel_servlet.model.entity.RoomStatus;
import com.dkoroliuk.hotel_servlet.model.entity.RoomType;

public class RoomDAOMock implements RoomDAO {
	private List<Room> rooms;
	private List<RoomStatus> roomStatuses;
	private List<RoomType> roomTypes;
	
	public RoomDAOMock() {
		rooms = new ArrayList<Room>();
		roomStatuses = new ArrayList<RoomStatus>();
		roomTypes = new ArrayList<RoomType>();
		Room room = new Room();
		room.setId(1);
		room.setNumber(1);
		room.setPerdayCost(1);
		room.setSeatsAmount(1);
		room.setDescription("1");
		RoomStatus roomStatus = new RoomStatus();
		roomStatus.setId(1);
		roomStatus.setStatus("1");
		room.setRoomStatus(roomStatus);
		roomStatuses.add(roomStatus);
		RoomType roomType = new RoomType();
		roomType.setId(1);
		roomType.setType("1");
		roomTypes.add(roomType);
		room.setRoomType(roomType);
		rooms.add(room);
		room = new Room();
		room.setId(2);
		room.setNumber(2);
		room.setPerdayCost(2);
		room.setSeatsAmount(2);
		room.setDescription("2");
		roomStatus = new RoomStatus();
		roomStatus.setId(2);
		roomStatus.setStatus("2");
		room.setRoomStatus(roomStatus);
		roomStatuses.add(roomStatus);
		roomType = new RoomType();
		roomType.setId(2);
		roomType.setType("2");
		roomTypes.add(roomType);
		room.setRoomType(roomType);
		rooms.add(room);
	}

	@Override
	public List<RoomStatus> getAllRoomStatuses() throws DBException {
		return roomStatuses;
	}

	@Override
	public List<RoomType> getAllRoomTypes() throws DBException {
		return roomTypes;
	}

	@Override
	public void addRoom(Room room) throws DBException {
		rooms.add(room);
		
	}

	@Override
	public int roomsCount(String sort, String order, String seatsAmount, String perdayCost, String roomStatus,
			String roomType) throws DBException {
		return rooms.size();
	}

	@Override
	public List<Room> getAllRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount,
			String perdayCost, String roomStatus, String roomType) throws DBException {
		return rooms;
	}

	@Override
	public Room getRoomById(long id) throws DBException {
		return rooms.stream().filter(room -> room.getId()==id).findFirst().orElse(null);
	}

	@Override
	public void updateRoom(Room room) throws DBException {
		Room roomStored = rooms.stream().filter(e -> e.getId() == room.getId()).findFirst().orElse(null);
		if (roomStored != null) {
			roomStored.setNumber(room.getNumber());
			roomStored.setPerdayCost(room.getPerdayCost());
			roomStored.setSeatsAmount(room.getSeatsAmount());
			roomStored.setDescription(room.getDescription());
			roomStored.setRoomType(room.getRoomType());
			roomStored.setRoomStatus(room.getRoomStatus());
		}
	}

	@Override
	public void deleteRoom(Room room) throws DBException {
		rooms.remove(room);
	}

	@Override
	public List<Room> getAllFreeRooms(int page, int roomsPerPage, String sort, String order, String seatsAmount,
			String perdayCost, String roomStatus, String roomType) throws DBException {
		return rooms.stream().filter(room->room.getRoomStatus().getStatus().equals("free")).toList();
	}

}
