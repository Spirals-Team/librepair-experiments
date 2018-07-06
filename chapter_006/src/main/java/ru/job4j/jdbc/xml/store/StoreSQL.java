package ru.job4j.jdbc.xml.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jdbc.xml.connect.ConnectionSqlite;
import ru.job4j.jdbc.xml.load.Config;

import java.sql.*;

/**
 * @author Yury Matskevich
 */
public class StoreSQL {
	private static final Logger LOG = LoggerFactory
			.getLogger(StoreSQL.class);
	private Config conf;
	private String queryInsert;
	private String queryClear;

	public StoreSQL(Config conf) {
		this.conf = conf;
		queryInsert = conf.getProperty("db.generateRow");
		queryClear = conf.getProperty("db.clearTable");
	}

	/**
	 * Generates some notes in a db
	 * @param n how many notes will be in the db
	 */
	public void generate(int n) {
		try (Connection conn = new ConnectionSqlite(conf).getConnection()) {
			clearTable(conn); //cleaning the table of the db
			int i;
			for (i = 1; i <= n; i++) {
				doQuery(conn, queryInsert, i);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * a method for doing query to a db with one parameter.<br>
	 * The parameter is integer.
	 * @param conn a current connection to a db
	 * @param query a query to the db
	 * @param num a number wich will be filled into the db
	 */
	private void doQuery(Connection conn, String query, int num) {
		try (PreparedStatement st = conn.prepareStatement(query)) {
			st.setInt(1, num);
			st.execute();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Clears all notes in a db
	 * @param conn a current connection to the db
	 */
	private void clearTable(Connection conn) {
		try (Statement st = conn.createStatement()) {
			st.executeQuery(queryClear);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
