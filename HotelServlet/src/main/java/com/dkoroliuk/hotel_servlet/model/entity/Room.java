package com.dkoroliuk.hotel_servlet.model.entity;

public class Room {

	private long id;

	private int number;

	private int seatsAmount;

	private int perdayCost;

	private RoomStatus roomStatus;

	private RoomType roomType;

	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSeatsAmount() {
		return seatsAmount;
	}

	public void setSeatsAmount(int seatsAmount) {
		this.seatsAmount = seatsAmount;
	}

	public int getPerdayCost() {
		return perdayCost;
	}

	public void setPerdayCost(int perdayCost) {
		this.perdayCost = perdayCost;
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
