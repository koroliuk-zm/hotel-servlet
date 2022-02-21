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
import com.dkoroliuk.hotel_servlet.model.entity.User;
import com.dkoroliuk.hotel_servlet.model.entity.UserRole;

/**
 * Implementation of {@link UserDAO} interface
 */
public class UserDAOImpl implements UserDAO {
	private static UserDAO instance;
	private static final Logger logger;

	static {
		logger = LogManager.getLogger(UserDAOImpl.class.getName());
	}

	/**
	 * Protects constructor to deny direct instantiation
	 */
	private UserDAOImpl() {
	}

	/**
	 * Method to get instance of {@link UserDAO}
	 * 
	 * @return instance of {@link UserDAO}
	 */
	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAOImpl();
		}
		return instance;
	}

	/**
	 * Adds given {@link User} to data base
	 * 
	 * @param user given {@link User}
	 * @throws DBException in case of {@link SQLException}
	 */
	@Override
	public void registerUser(User user) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.REGISTER_USER,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			ps.setString(k++, user.getLogin());
			ps.setBytes(k++, user.getPassword());
			ps.setBytes(k++, user.getSalt());
			ps.setString(k++, user.getName());
			ps.setString(k++, user.getSurname());
			ps.setString(k++, user.getEmail());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				user.setId(rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Can`t register new user", e);
			throw new DBException("Can`t register new user", e);
		}
	}

	@Override
	public User getUserByLogin(String login) throws DBException {
		User user = null;
		if (login == null)
			return null;
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.GET_USER_BY_LOGIN)) {
			ps.setString(1, login);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					user = new User();
					int k = 1;
					user.setId(rs.getLong(k++));
					UserRole userRole = new UserRole();
					userRole.setId(rs.getInt(k++));
					userRole.setRole(rs.getString(k++));
					user.setUserRole(userRole);
					user.setPassword(rs.getBytes(k++));
					user.setSalt(rs.getBytes(k++));
					user.setEnable(rs.getBoolean(k++));
				}
			}
		} catch (SQLException e) {
			logger.error("Can`t get user by login", e);
			throw new DBException("Can`t get user by login", e);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws DBException {
		User user = new User();
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.GET_USER_BY_ID)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				int k = 1;
				user.setId(rs.getLong(k++));
				user.setLogin(rs.getString(k++));
				user.setName(rs.getString(k++));
				user.setSurname(rs.getString(k++));
				user.setEmail(rs.getString(k++));
				user.setEnable(rs.getBoolean(k++));
				UserRole userRole = new UserRole();
				userRole.setId(rs.getInt(k++));
				userRole.setRole(rs.getString(k++));
				user.setUserRole(userRole);
			}
		} catch (SQLException e) {
			logger.error("Can`t get user by id", e);
			throw new DBException("Can`t get user by id", e);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery.GET_ALL_USERS)) {
			while (rs.next()) {
				User user = new User();
				int k = 1;
				user.setId(rs.getLong(k++));
				user.setLogin(rs.getString(k++));
				user.setName(rs.getString(k++));
				user.setSurname(rs.getString(k++));
				UserRole userRole = new UserRole();
				userRole.setId(rs.getInt(k++));
				userRole.setRole(rs.getString(k++));
				user.setUserRole(userRole);
				user.setEmail(rs.getString(k++));
				user.setEnable(rs.getBoolean(k++));
				users.add(user);
			}
		} catch (SQLException e) {
			logger.error("Can`t get all users", e);
			throw new DBException("Can`t get all users", e);
		}
		return users;
	}

	@Override
	public List<UserRole> getAllUserRoles() throws DBException {
		List<UserRole> userRoles = new ArrayList<UserRole>();
		try (Connection con = PooledConnections.getInstance().getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(SqlQuery.GET_ALL_USERROLES)) {
			while (rs.next()) {
				UserRole userRole = new UserRole();
				int k = 1;
				userRole.setId(rs.getInt(k++));
				userRole.setRole(rs.getString(k++));
				userRoles.add(userRole);
			}
		} catch (SQLException e) {
			logger.error("Can`t get all user roles", e);
			throw new DBException("Can`t get all user roles", e);
		}
		return userRoles;
	}

	@Override
	public void updateUser(User user) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.UPDATE_USER)) {
			int k = 1;
			ps.setString(k++, user.getLogin());
			ps.setString(k++, user.getName());
			ps.setString(k++, user.getSurname());
			ps.setInt(k++, user.getUserRole().getId());
			ps.setBytes(k++, user.getPassword());
			ps.setString(k++, user.getEmail());
			ps.setBytes(k++, user.getSalt());
			ps.setBoolean(k++, user.isEnable());
			ps.setLong(k++, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can`t update user", e);
			throw new DBException("Can`t update user", e);
		}

	}

	@Override
	public void deleteUser(User user) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.DELETE_USER)) {
			int k = 1;
			ps.setLong(k++, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("Can`t delete user", e);
			throw new DBException("Can`t delete user", e);
		}

	}

	@Override
	public void addUser(User user) throws DBException {
		try (Connection con = PooledConnections.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(SqlQuery.ADD_USER,
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			ps.setString(k++, user.getLogin());
			ps.setBytes(k++, user.getPassword());
			ps.setBytes(k++, user.getSalt());
			ps.setString(k++, user.getName());
			ps.setString(k++, user.getSurname());
			ps.setString(k++, user.getEmail());
			ps.setInt(k++, user.getUserRole().getId());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				user.setId(rs.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Can`t create new user", e);
			throw new DBException("Can`t create new user", e);
		}

	}

}
