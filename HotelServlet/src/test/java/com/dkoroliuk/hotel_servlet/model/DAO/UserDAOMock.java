package com.dkoroliuk.hotel_servlet.model.DAO;

import java.util.ArrayList;
import java.util.List;

import com.dkoroliuk.hotel_servlet.exception.DBException;
import com.dkoroliuk.hotel_servlet.model.DAO.UserDAO;
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;

public class UserDAOMock implements UserDAO {
	private List<User> users;
	private List<UserRole> userRoles;

	public UserDAOMock() {
		userRoles = new ArrayList<UserRole>();
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setRole("admin");
		userRoles.add(userRole);
		userRole = new UserRole();
		userRole.setId(2);
		userRole.setRole("waiter");
		userRoles.add(userRole);
		userRole = new UserRole();
		userRole.setId(3);
		userRole.setRole("client");
		userRoles.add(userRole);

		users = new ArrayList<User>();
		User user = new User();
		user.setId(1);
		user.setLogin("1");
		user.setName("1");
		user.setSurname("1");
		user.setEnable(true);
		user.setEmail("1");
		user.setUserRole(userRoles.get(1));
		users.add(user);
		user = new User();
		user.setId(2);
		user.setLogin("2");
		user.setName("2");
		user.setSurname("2");
		user.setEnable(true);
		user.setEmail("2");
		user.setUserRole(userRoles.get(2));
		users.add(user);
	}

	@Override
	public User getUserByLogin(String login) throws DBException {
		return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
	}

	@Override
	public void registerUser(User user) throws DBException {
		users.add(user);
	}

	@Override
	public User getUserById(Long id) throws DBException {
		return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
	}

	@Override
	public List<User> getAllUsers() throws DBException {
		return users;
	}

	@Override
	public List<UserRole> getAllUserRoles() throws DBException {
		return userRoles;
	}

	@Override
	public void updateUser(User user) throws DBException {
		User userStored = users.stream().filter(e -> e.getId() == user.getId()).findFirst().orElse(user);
		if (userStored != null) {
			// copy values
		}
	}

	@Override
	public void deleteUser(User user) throws DBException {
		users.remove(user);

	}

	@Override
	public void addUser(User user) throws DBException {
		users.add(user);
	}

}
