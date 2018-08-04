package ru.job4j.crud.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.crud.load.LoadResource;
import ru.job4j.crud.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class DbStore implements Store {
	private static final Logger LOG = Logger.getLogger(DbStore.class);
	private static final BasicDataSource SOURCE = new BasicDataSource();
	private static DbStore uniqueInstance = new DbStore();
	private LoadResource res = new LoadResource("/db.properties");

	private DbStore() {
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

	public static DbStore getInstance() {
		return uniqueInstance;
	}

	@Override
	public boolean add(User user) {
		boolean result = false;
		String query = res.getProperty("db.queryAdd");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setString(1, user.getName());
			st.setString(2, user.getLogin());
			st.setString(3, user.getEmail());
			st.setLong(4, user.getCreateDate());
			st.setString(5, user.getPassword());
			st.setInt(6, user.getRole());
			st.setInt(7, user.getCityId());
			result = st.executeUpdate() == 1;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public boolean update(User user) {
		boolean result = false;
		String query = res.getProperty("db.queryUpdate");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setString(1, user.getName());
			st.setString(2, user.getLogin());
			st.setString(3, user.getEmail());
			st.setString(4, user.getPassword());
			st.setInt(5, user.getRole());
			st.setInt(6, user.getCityId());
			st.setInt(7, user.getId());
			result = st.executeUpdate() == 1;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;
		String query = res.getProperty("db.queryDelete");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setInt(1, id);
			result = st.executeUpdate() == 1;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		String query = res.getProperty("db.queryFindAll");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query);
			 ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				users.add(
						new User(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getLong(5),
								rs.getString(6),
								rs.getInt(7),
								rs.getInt(8)
						)
				);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return users;
	}

	@Override
	public User findById(int id) {
		String query = res.getProperty("db.queryFindById");
		User user = null;
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					user = new User(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getLong(5),
							rs.getString(6),
							rs.getInt(7),
							rs.getInt(8)
					);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return user;
	}

	@Override
	public User findByLogin(String login) {
		String query = res.getProperty("db.queryFindByLogin");
		User user = null;
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query)) {
			st.setString(1, login);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					user = new User(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getLong(5),
							rs.getString(6),
							rs.getInt(7),
							rs.getInt(8)
					);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return user;
	}

	@Override
	public List<String> getLogins() {
		List<String> logins = new ArrayList<>();
		String query = res.getProperty("db.queryGetLogins");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query);
			 ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				logins.add(rs.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return logins;
	}

	@Override
	public List<String> getEmails() {
		List<String> logins = new ArrayList<>();
		String query = res.getProperty("db.queryGetEmails");
		try (Connection connection = SOURCE.getConnection();
			 PreparedStatement st = connection.prepareStatement(query);
			 ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				logins.add(rs.getString(1));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return logins;
	}
}
