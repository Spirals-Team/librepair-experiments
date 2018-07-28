package ru.job4j.crud.listners;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Yury Matskevich
 */
public class ConnectionDb implements AutoCloseable {
	private static final Logger LOG = Logger.getLogger(ConnectionDb.class);
	private Connection conn;
	private String url;
	private String username;
	private String password;

	/**
	 * Create an object wich allows to get a <code>Connection</code>
	 * to a current db
	 * @param url a url of a current db server
	 * @param username a username of a current db
	 * @param password a password for getting an access to the current db
	 */
	public ConnectionDb(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Create a connection to the db
	 * @return <code>Connection</code> to the current db
	 */
	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage(), e);
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return conn;
	}

	@Override
	public void close() throws Exception {
		if (conn != null) {
			conn.close();
		}
	}
}
