package ru.job4j.jdbc.tracker.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
	private String pathScripDb;
	private String pathScripStructur;

	public PostgreDb(String path,
					 String pathScripDb,
					 String pathScripStructur) {
		storeConnectionDate(new File(path));
		this.pathScripDb = pathScripDb;
		this.pathScripStructur = pathScripStructur;
	}

	private void storeConnectionDate(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			Properties dbProperties = new Properties();
			dbProperties.load(fis);
			path = dbProperties.getProperty("db.path");
			name = dbProperties.getProperty("db.name");
			username = dbProperties.getProperty("db.username");
			password = dbProperties.getProperty("db.password");
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private String getQuery(String path) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(
				new FileReader(path))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return contentBuilder.toString();
	}

	private Connection getConnection(String url,
									 String username,
									 String password) {
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

	@Override
	public Connection connect() {
		Connection conn;
		Statement statement;
		try {
			conn = DriverManager.getConnection(
					String.format("%s%s?currentSchema = public", path, name),
					username,
					password
			);
		} catch (Exception e) {
			conn = getConnection(
					String.format("%s%s", path, "postgres"),
					username,
					password
			);
			try {
				statement = conn.createStatement();
				statement.execute(getQuery(pathScripDb));
				conn.close();
			} catch (SQLException e1) {
				LOG.error(e.getMessage(), e);
			}
			conn = getConnection(
					String.format("%s%s", path, name),
					username,
					password
			);
			try {
				statement = conn.createStatement();
				statement.execute(getQuery(pathScripStructur));
			} catch (SQLException e1) {
				LOG.error(e.getMessage(), e);
			}
		}
		return conn;
	}
}
