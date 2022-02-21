package com.dkoroliuk.hotel_servlet.model.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String type) {
		this.role = type;
	}

}
