package com.dkoroliuk.hotel_servlet.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Data source 
 */
public class PooledConnections {
	private static DataSource instance;

	/**
	 * Protect constructor to deny direct instantiation
	 */
	private PooledConnections() {
	}

	/**
	 * Method to get instance of {@link DataSource}
	 * @return instance of {@link DataSource}
	 */
	public static DataSource getInstance() {
		return instance;
	}

	/**
	 * Initialization method for {@link DataSource} through {@link Context}
	 */
	public static void init() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			instance = (DataSource) envContext.lookup("jdbc/Hotel");
		} catch (NamingException ex) {
			throw new IllegalStateException("Cannot init DBManager", ex);
		}
	}

}
