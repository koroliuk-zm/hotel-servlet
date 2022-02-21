package com.dkoroliuk.hotel_servlet.model.entity;

public class RoomStatus {
	private static final long serialVersionUID = 1L;
	private int id;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
