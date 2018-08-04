package ru.job4j.last.connection;

import org.apache.log4j.Logger;
import ru.job4j.last.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yury Matskevich
 */
public class ConnectionSqlite implements IConnection, AutoCloseable {
	private static final Logger LOG = Logger.getLogger(ConnectionSqlite.class);
	//path to resource folder where is a db in.
	private String res = getClass().getClassLoader().getResource("offers.bd").getPath();
	private Connection conn;
	private String path;
	private String scriptTable;

	/**
	 * Creates object of connection to the SQLite db.
	 * @param conf an object of {@link Config} where it is
	 * all the settings to the db
	 */
	public ConnectionSqlite(Config conf) {
		path = conf.getProperty("jdbc.path");
		scriptTable = conf.getProperty("jdbc.createTable");
		createTable(connect());
	}

	/**
	 * Converts delemiters of resource's path from '\' to '/'
	 * if it is necessary
	 * @return a string of resourc where a delimiter is '/'
	 */
	private String convertPathIfNecessary() {
		String path = res;
		if (path.contains("\\")) {
			path = path.replace("\\", "/");
		}
		return path;
	}

	/**
	 * Launche script for creating a table in a db
	 * @param conn a connection to the db
	 */
	private void createTable(Connection conn) {
		try (Statement st = conn.createStatement()) {
			st.execute(scriptTable);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public Connection connect() {
		try {
			conn = DriverManager.getConnection(
					String.format("%s%s", path, convertPathIfNecessary()));
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
