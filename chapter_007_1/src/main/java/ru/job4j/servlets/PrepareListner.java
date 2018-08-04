package ru.job4j.servlets;

import org.apache.log4j.Logger;
import ru.job4j.model.load.LoadResource;
import ru.job4j.model.pojo.Address;
import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;
import ru.job4j.model.repository.UserSqlRepository;
import ru.job4j.model.store.StoreDb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Yury Matskevich
 */
public class PrepareListner implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(PrepareListner.class);
	private StoreDb store = StoreDb.getInstance();
	private UserSqlRepository repository = UserSqlRepository.getInstance();
	private LoadResource res = new LoadResource("/db.properties");

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		createDb();
		filltebles();
		addAdmin();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		dropSchema();
	}

	private void createDb() {
		String query = res.getProperty("db.createTable");
		try (Connection conn = store.getConnection();
			 Statement statement = conn.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private void filltebles() {
		String query = res.getProperty("db.fillRolesMusic");
		try (Connection conn = store.getConnection();
			 Statement statement = conn.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private void addAdmin() {
		repository.add(
				new User(
						"ADMIN",
						"ADMIN",
						"ADMIN",
						new Address("Belarus", "Brest"),
						Roles.ADMIN,
						new ArrayList<MusicType>()
				)
		);
	}

	//just for useeful testing
	private void dropSchema() {
		String query = "DROP SCHEMA public CASCADE; CREATE SCHEMA public;";
		try (Connection conn = store.getConnection();
			 Statement statement = conn.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
