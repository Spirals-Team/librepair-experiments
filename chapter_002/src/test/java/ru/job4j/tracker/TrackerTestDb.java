package ru.job4j.tracker;

import org.junit.After;
import ru.job4j.tracker.connect.PostgreDb;
import ru.job4j.tracker.dao.ITracker;
import ru.job4j.tracker.dao.TrackerDb;
import ru.job4j.tracker.load.LoadResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yury Matskevich
 */
public class TrackerTestDb extends TrackerTest {
	private String conf = "/config.properties";

	//for deleting database after each test
	@After
	public void deleteDb() throws SQLException {
		LoadResource res = new LoadResource(conf);
		try (Connection conn = DriverManager.getConnection(
				String.format(
						"%s%s", res.getProperty("db.path"), "postgres"
				),
				res.getProperty("db.username"),
				res.getProperty("db.password"));
			 Statement st = conn.createStatement()) {
			st.execute(
					String.format(
							"DROP DATABASE %s", res.getProperty("db.name")
					)
			);
		}
	}

	@Override
	protected ITracker getTracker() {
		return new TrackerDb(new PostgreDb(conf));
	}
}
