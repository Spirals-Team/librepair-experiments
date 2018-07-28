package ru.job4j.jdbc.xml.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jdbc.tracker.connect.PostgreDb;
import ru.job4j.jdbc.xml.load.Config;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yury Matskevich
 */
public class ConnectionSqlite implements AutoCloseable {
	private static final Logger LOG = LoggerFactory
			.getLogger(PostgreDb.class);
	//path to resource folder. Where is a db ib in the folder
	private String res =
			Paths.get(
					"src",
					"test",
					"resources",
					"new.bd"
			).toString();
	private Connection conn;
	private Config conf;
	private String path;
	private String scriptTable;

	public ConnectionSqlite(Config conf) {
		this.conf = conf; //a configuration of connection
		fill();
	}

	private String convertPathIfNecessary() {
		String path = res;
		if (path.contains("\\")) {
			path = path.replace("\\", "/");
		}
		return path;
	}

	//create a db and a table
	private void fill() {
		path = conf.getProperty("db.path");
		scriptTable = conf.getProperty("db.createTable");
	}

	//create table
	private void createTable(Connection conn) {
		try (Statement st = conn.createStatement()) {
			st.executeQuery(scriptTable);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Gets connection to a current db
	 *
	 * @return a connection to the db
	 */
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(
					String.format("%s%s", path, convertPathIfNecessary()));
			createTable(conn);
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
