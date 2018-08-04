package ru.job4j.model.repository;

import org.apache.log4j.Logger;
import ru.job4j.model.pojo.Address;
import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;
import ru.job4j.model.load.LoadResource;
import ru.job4j.model.repository.queries.Specification;
import ru.job4j.model.repository.queries.SqlSpecification;
import ru.job4j.model.store.StoreDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class UserSqlRepository implements Repository<User> {
	private static final Logger LOG = Logger.getLogger(UserSqlRepository.class);
	private LoadResource res = new LoadResource("/db.properties");
	private static UserSqlRepository uniqueInstance = new UserSqlRepository();
	private StoreDb store = StoreDb.getInstance();

	private UserSqlRepository() {

	}

	public static UserSqlRepository getInstance() {
		return uniqueInstance;
	}

	@Override
	public boolean add(User user) {
		boolean result = false;
		String query1 = res.getProperty("db.queryAdd");
		String query2 = res.getProperty("db.queryAddMusic");
		try (Connection connection = store.getConnection()) {
			try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
				connection.setAutoCommit(false);
				statement1.setString(1, user.getName());
				statement1.setInt(2, user.getRole().getId());
				statement1.setString(3, user.getLogin());
				statement1.setString(4, user.getPassword());
				Address address = user.getAddress();
				statement1.setString(5, address.getCountry());
				statement1.setString(6, address.getCity());
				try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
					List<MusicType> music = user.getMusicsType();
					for (MusicType item : music) {
						statement2.setInt(1, item.getId());
						statement2.addBatch();
					}
					result = statement1.executeUpdate() == 0
							& statement2.executeBatch().length == music.size();
				}
				connection.commit();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				connection.rollback();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public boolean update(User user) {
		boolean result = false;
		String query1 = res.getProperty("db.queryUpdateUser");
		String query2 = res.getProperty("db.queryUpdateCredential");
		String query3 = res.getProperty("db.queryUpdateAddress");
		String query4 = res.getProperty("db.queryDeleteMusic");
		String query5 = res.getProperty("db.queryUpdateMusic");
		int id = user.getId();
		try (Connection connection = store.getConnection()) {
			connection.setAutoCommit(false);
			try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
				statement1.setString(1, user.getName());
				statement1.setInt(2, user.getRole().getId());
				statement1.setInt(3, id);
				try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
					statement2.setString(1, user.getLogin());
					statement2.setString(2, user.getPassword());
					statement2.setInt(3, id);
					try (PreparedStatement statement3 = connection.prepareStatement(query3)) {
						Address address = user.getAddress();
						statement3.setString(1, address.getCountry());
						statement3.setString(2, address.getCity());
						statement3.setInt(3, id);
						try (PreparedStatement statement4 = connection.prepareStatement(query4)) {
							statement4.setInt(1, id);
							try (PreparedStatement statement5 = connection.prepareStatement(query5)) {
								List<MusicType> music = user.getMusicsType();
								for (MusicType item : music) {
									statement5.setInt(1, id);
									statement5.setInt(2, item.getId());
									statement5.addBatch();
								}
								result = statement1.executeUpdate() == 1
										& statement2.executeUpdate() == 1
										& statement3.executeUpdate() == 1
										& !statement4.execute()
										& statement5.executeBatch().length == music.size();
							}
						}
					}
				}
				connection.commit();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				connection.rollback();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public boolean delete(int id) {
		boolean result = false;
		String query = res.getProperty("db.queryDeleteUser");
		try (Connection connection = store.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			result = statement.executeUpdate() == 1;
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<User> query(Specification specification) {
		List<User> users = new ArrayList<>();
		SqlSpecification sqlSpecification = (SqlSpecification) specification;
		String query = String.format("%s %s;", res.getProperty("db.query"), sqlSpecification.toSqlQuery());
		try (Connection connection = store.getConnection();
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				users.add(
						UserMapper.toUserMap(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getString(7),
								rs.getArray(8).getArray()
						)
				);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return users;
	}
}
