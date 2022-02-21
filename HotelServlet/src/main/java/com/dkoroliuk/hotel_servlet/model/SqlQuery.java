package com.dkoroliuk.hotel_servlet.model;

/**
 * Utility class that holds sql queries
 */
public class SqlQuery {

	private SqlQuery() {
	}

	// User DAO

	public static final String GET_USER_BY_LOGIN = "SELECT u.id, ur.id, ur.role, u.password, u.salt, u.is_enable FROM user u JOIN user_role ur WHERE u.login = ? AND ur.id = u.role_id;";

	public static final String REGISTER_USER = "INSERT INTO user (login, password, salt, name, surname, email) VALUES (?, ?, ?, ?, ?, ?);";

	public static final String ADD_USER = "INSERT INTO user (login, password, salt, name, surname, email, role_id) VALUES (?, ?, ?, ?, ?, ?, ?);";

	public static final String GET_USER_BY_ID = "SELECT u.id, u.login, u.name, u.surname, u.email, u.is_enable, ur.id , ur.role "
			+ "FROM user u JOIN user_role ur WHERE u.id = ? AND ur.id = u.role_id;";

	public static final String GET_ALL_USERS = "SELECT u.id, u.login, u.name, u.surname, ur.id, ur.role, u.email, u.is_enable "
			+ "FROM user u JOIN user_role ur WHERE ur.id = u.role_id;";

	public static final String GET_ALL_USERROLES = "SELECT id, role FROM user_role;";

	public static final String UPDATE_USER = "UPDATE user SET login= ?, name= ?, surname = ?, "
			+ "role_id = ? , password = ? , email=?, salt = ? , is_enable = ? " + "WHERE id= ?;";

	public static final String DELETE_USER = "DELETE FROM user WHERE id=? AND role_id=3;";

	// Room DAO
	public static final String GET_ALL_ROOMSTATUSES = "SELECT id, status FROM room_status;";

	public static final String GET_ALL_ROOMTYPES = "SELECT id, type FROM room_type;";

	public static final String ADD_ROOM = "INSERT INTO room (number, seats_amount, perday_cost, room_statuses_id, room_types_id, description) "
			+ "VALUES (?, ?, ?, ?, ?, ?);";

	public static final String GET_ROOM_BY_ID = "SELECT r.id, r.number, r.seats_amount, r.perday_cost, rs.id, rs.status, rt.id, rt.type, r.description "
			+ "FROM room r INNER JOIN room_status rs ON rs.id = r.room_statuses_id "
			+ "INNER JOIN room_type rt ON rt.id = r.room_types_id where r.id = ?;";

	public static final String UPDATE_ROOM = "UPDATE room SET number= ?, seats_amount = ?, perday_cost = ?, "
			+ "room_statuses_id = ? , room_types_id = ? , description =? " + "WHERE id= ?;";

	public static final String DELETE_ROOM = "DELETE FROM room WHERE id=? AND room_statuses_id IN (1,4);";

	public static final String CHANGE_ROOM_STATUS_TO_BOOKED = "UPDATE room SET room_statuses_id = 2 WHERE id = ?;";
	
	public static final String CHANGE_ROOM_STATUS_TO_BOOKED_BY_NUMBER = "UPDATE room SET room_statuses_id = 2 WHERE number = ?;";
	
	public static final String SET_ROOM_STATUS_BUSY = "UPDATE room AS r INNER JOIN orders AS o ON o.room_id = r.id SET r.room_statuses_id = 3 WHERE o.id = ?;";

	public static final String SET_ROOM_STATUS_FREE = "UPDATE room AS r INNER JOIN orders AS o ON o.room_id = r.id SET r.room_statuses_id = 1 WHERE o.id = ?;";

	// Order DAO
	public static final String ADD_ROOM_ORDER = "INSERT INTO orders (users_id, room_id, order_date, "
			+ "check_in_date, check_out_date, order_status_id) " + "VALUES (?, ?, now(), ?, ?, 2);";

	public static final String GET_UNPAID_USER_ORDERS = "SELECT o.id, o.users_id, u.login, u.name, u.surname, o.room_id, "
			+ "r.number, o.order_date, o.check_in_date, o.check_out_date, b.total_price, "
			+ "o.order_status_id, os.status "
			+ "FROM orders o JOIN user u JOIN room r JOIN order_status os JOIN bill b "
			+ "WHERE o.order_status_id = os.id and o.users_id = u.id "
			+ "and o.room_id = r.id and o.id = b.order_id and o.users_id =? and o.order_status_id <3 "
			+ "ORDER BY o.order_date;";

	public static final String GET_USER_HISTORY = "SELECT o.id, o.users_id, u.login, u.name, u.surname, o.room_id, "
			+ "r.number, o.order_date, o.check_in_date, o.check_out_date, b.total_price, "
			+ "o.order_status_id, os.status "
			+ "FROM orders o JOIN user u JOIN room r JOIN order_status os JOIN bill b "
			+ "WHERE o.order_status_id = os.id and o.users_id = u.id "
			+ "and o.room_id = r.id and o.id = b.order_id and o.users_id =? and o.order_status_id >2 "
			+ "ORDER BY o.order_date;";
	
	public static final String SET_ORDER_STATUS_EXPIRED = "UPDATE orders AS o SET o.order_status_id = 4 WHERE o.id = ?;";
	
	public static final String GET_ALL_ORDERS = "SELECT o.id, o.users_id, u.login, u.name, u.surname, o.room_id, "
			+ "r.number, o.order_date, o.check_in_date, o.check_out_date, b.total_price, "
			+ "o.order_status_id, os.status "
			+ "FROM orders o JOIN user u JOIN room r JOIN order_status os JOIN bill b "
			+ "WHERE o.order_status_id = os.id and o.users_id = u.id "
			+ "and o.room_id = r.id and o.id = b.order_id ORDER BY o.order_date;";
	
	public static final String DELETE_ORDER = "DELETE FROM orders WHERE id=?";
	
	public static final String SET_ORDER_STATUS_PAID = "UPDATE orders SET order_status_id = 3 WHERE id = ?;";
	public static final String SET_ORDER_STATUS_CLOSED = "UPDATE orders SET order_status_id = 5 WHERE id = ?;";

//	Request DAO
	public static final String ADD_REQUEST = "INSERT INTO request (users_id, seats_number, room_types_id, request_date, "
			+ "check_in_date, check_out_date) " + "VALUES (?, ?, ?, now(), ?, ?);";

	public static final String GET_USER_REQUESTS = "SELECT r.users_id, u.login, u.name, u.surname, r.request_date, r.check_in_date, r.check_out_date, r.room_types_id, rt.type "
			+ "FROM request r " + "JOIN user  u " + "JOIN room_type rt "
			+ "WHERE r.users_id = u.id and r.room_types_id = rt.id and r.users_id = ? " + "ORDER BY r.request_date;";

	public static final String GET_ALL_REQUESTS = "SELECT r.id, r.users_id, u.login, u.name, u.surname, r.request_date, r.check_in_date, r.check_out_date, r.room_types_id, rt.type, r.seats_number "
			+ "FROM request r " + "JOIN user  u " + "JOIN room_type rt "
			+ "WHERE r.users_id = u.id and r.room_types_id = rt.id " + "ORDER BY r.request_date;";

	public static final String GET_REQUEST_BY_ID = "SELECT r.id, r.seats_number, r.request_date, r.check_in_date, r.check_out_date,  rt.id, rt.type, "
			+ " u.id, u.login, u.name, u.surname "
			+ "FROM request r INNER JOIN room_type rt ON rt.id = r.room_types_id "
			+ "INNER JOIN user u ON u.id = r.users_id where r.id = ?;";

	public static final String CREATE_ORDER_BY_REQUEST = "INSERT INTO orders (check_in_date, check_out_date, order_date, users_id, order_status_id, room_id) "
			+ "Values ((select check_in_date from request where id=?), "
			+ "(select check_out_date from request where id = ?), " + "now(), "
			+ " (select users_id from request where id =?), " + " 2, " + " (select id from room where number = ?))";

	public static final String DELETE_REQUEST = "DELETE FROM request WHERE id=?";

	

}
