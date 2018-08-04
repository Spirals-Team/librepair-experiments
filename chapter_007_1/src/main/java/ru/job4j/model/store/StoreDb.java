package ru.job4j.model.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.model.load.LoadResource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Yury Matskevich
 */
public class StoreDb {
	private static final Logger LOG = Logger.getLogger(StoreDb.class);
	private static final BasicDataSource SOURCE = new BasicDataSource();
	private static StoreDb uniqueInstance = new StoreDb();
	private LoadResource res = new LoadResource("/db.properties");

	private StoreDb() {
		SOURCE.setDriverClassName("org.postgresql.Driver");
		SOURCE.setUrl(res.getProperty("db.path"));
		SOURCE.setUsername(res.getProperty("db.username"));
		SOURCE.setPassword(res.getProperty("db.password"));
		SOURCE.setMinIdle(Integer.parseInt(res.getProperty("db.minIdle")));
		SOURCE.setMaxIdle(Integer.parseInt(res.getProperty("db.maxIdl")));
		SOURCE.setMaxOpenPreparedStatements(
				Integer.parseInt(res.getProperty("db.MaxOpenPreparedStatements"))
		);
		SOURCE.setValidationQuery(res.getProperty("db.validationQuery"));
	}

	public static StoreDb getInstance() {
		return uniqueInstance;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = SOURCE.getConnection();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return conn;
	}
}
