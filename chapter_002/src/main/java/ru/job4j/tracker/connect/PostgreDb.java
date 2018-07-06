package ru.job4j.tracker.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.load.LoadResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is for getting a connection to the
 * postgreSQL
 *
 * @author Yury Matskevich
 */
public class PostgreDb implements IConnection {
	private static final Logger LOG = LoggerFactory
			.getLogger(PostgreDb.class);
	private String path;
	private String name;
	private String username;
	private String password;
	private String createDb;
	private String createStructure;

	/**
	 * Create {@code PostgreDb} class for fetting
	 * connection to the database
	 *
	 * @param config a file with setting
	 */
	public PostgreDb(String config) {
		LoadResource res = new LoadResource(config);
		path = res.getProperty("db.path");
		name = res.getProperty("db.name");
		username = res.getProperty("db.username");
		password = res.getProperty("db.password");
		createDb = res.getProperty("db.createDbScript");
		createStructure = res.getProperty("db.createStructure");
	}

	//return connection
	private Connection getConnection(String url) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
					url,
					username,
					password
			);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return conn;
	}

	//executing a SQL script for current connection
	private void executeScript(Connection conn, String query) {
		try (Statement st = conn.createStatement()) {
			st.execute(query);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * The method for connecting to a user database
	 * if it exists or create the user database and connection to it
	 *
	 * @return a connection to the database
	 */
	@Override
	public Connection connect() {
		Connection conn;
		try {
			// try to connect with a user db if it exists
			conn = DriverManager.getConnection(
					String.format("%s%s", path, name),
					username,
					password
			);
		} catch (Exception e) {
			// if there is not a user db so try to connect with
			// a standart db(postgres) to create the user db
			conn = getConnection(String.format("%s%s", path, "postgres"));
			executeScript(conn, createDb); // create a user db
			try {
				conn.close();
			} catch (SQLException e1) {
				LOG.error(e.getMessage(), e1);
			}
			conn = getConnection(String.format("%s%s", path, name));
			executeScript(conn, createStructure); // create a user db structure
		}
		return conn;
	}
}
