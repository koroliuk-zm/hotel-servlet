package com.dkoroliuk.hotel_servlet.model.DAO;

import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;

/**
 * Interface for {@link User} DAO
 */
public interface UserDAO {
	User getUserByLogin(String login) throws DBException;

	void registerUser(User user) throws DBException;

	User getUserById(Long id) throws DBException;

	List<User> getAllUsers() throws DBException;

	List<UserRole> getAllUserRoles() throws DBException;

	void updateUser(User user) throws DBException;

	void deleteUser(User user) throws DBException;

	void addUser(User user) throws DBException;
}
