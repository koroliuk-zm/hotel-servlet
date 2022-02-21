package com.dkoroliuk.hotel_servlet.model.DAO;

import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.entity.Request;

public interface RequestDAO {

	void addRequest(Request request, Long userId) throws DBException;

	List<Request> getUserRequests(Long userId) throws DBException;

	List<Request> getAllRequests() throws DBException;

	Request getRequestById(Long requestId) throws DBException;

	void processRequest(Long requestId, Integer roomNumber) throws DBException;

}
