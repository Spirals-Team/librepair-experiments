package ru.job4j.model.repository;

import ru.job4j.model.load.LoadResource;
import ru.job4j.model.store.StoreDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yury Matskevich
 */
public class CreateDb {
	private StoreDb store = StoreDb.getInstance();
	private LoadResource res = new LoadResource("/db.properties");

	public void createDb() {
		String query = res.getProperty("db.createTable");
		try (Connection conn = store.getConnection();
			 Statement statement = conn.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			//
		}
	}

	public void filltebles() {
		String query = res.getProperty("db.fillRolesMusic");
		try (Connection conn = store.getConnection();
			 Statement statement = conn.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			//
		}
	}
}
