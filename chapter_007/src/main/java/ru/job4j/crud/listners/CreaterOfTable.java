package ru.job4j.crud.listners;

import org.apache.log4j.Logger;
import ru.job4j.crud.load.LoadResource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;

/**
 * @author Yury Matskevich
 */
public class CreaterOfTable implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(CreaterOfTable.class);
	private LoadResource res = new LoadResource("/db.properties");
	private ConnectionDb conn = new ConnectionDb(
			res.getProperty("db.path"),
			res.getProperty("db.username"),
			res.getProperty("db.password")
	);

	/**
	 * Creates a table (if it's not exist) to store user's information
	 * in the current db
	 */
	private void createTable() {
		String query = res.getProperty("db.createTable");
		try (Connection connection = conn.getConnection();
			 Statement st = connection.createStatement()) {
			st.execute(query);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * fills tables of the db
	 */
	private void fillTableRole() {
		String query = res.getProperty("db.fillTables");
		try (Connection connection = conn.getConnection();
			 Statement st = connection.createStatement()) {
			st.execute(query);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Add an admin user to the store
	 */
	private void addAdmin() {
		String query = res.getProperty("db.queryAdd");
		try (Connection connection = conn.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setString(1, "admin");
			st.setString(2, "admin");
			st.setString(3, "admin@gmail.com");
			st.setLong(4, System.currentTimeMillis());
			st.setString(5, "admin");
			st.setInt(6, 1);
			st.setInt(7, 1);
			st.execute();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		createTable();
		fillTableRole();
		addAdmin();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
