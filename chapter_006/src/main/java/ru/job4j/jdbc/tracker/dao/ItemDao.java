package ru.job4j.jdbc.tracker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jdbc.tracker.connect.IConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yury Matskevich
 */
public class ItemDao implements IItemDao {
	private static final Logger LOG = LoggerFactory
			.getLogger(ItemDao.class);
	private IConnection typeConn;

	public ItemDao(IConnection typeConn) {
		this.typeConn = typeConn;
	}

	@Override
	public Item add(Item item) {
		String query =
				"INSERT INTO items (name_i, description_i, create_i) "
				+ "VALUES (?, ?, ?)";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setString(1, item.getName());
			st.setString(2, item.getDescription());
			st.setDate(3, item.getCreate());
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return item;
	}

	@Override
	public Item update(int id, Item item) {
		String query =
				"UPDATE items "
				+ "SET "
				+ "name_i = ?, "
				+ "description_i = ?, "
				+ "create_i = ?"
				+ "WHERE id_i = ?;";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setString(1, item.getName());
			st.setString(2, item.getDescription());
			st.setDate(3, item.getCreate());
			st.setInt(4, id);
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return item;
	}

	@Override
	public void delete(int id) {
		String query = "DELETE FROM items WHERE id_i = ?";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public List<Item> findAll() {
		String query = ""; //for this query we don't need to have filter
		return getSetResult(query);
	}

	@Override
	public List<Item> findByName(String key) {
		String query = String.format(" WHERE i.name_i = '%s'", key);
		return getSetResult(query);
	}

	@Override
	public Item findById(int id) {
		String query = String.format(" WHERE i.id_i = %s", id);
		return getSetResult(query).get(0);
	}

	@Override
	public void writeComment(int id, String comment) {
		String query =
				"INSERT INTO commentss (comment_c, id_i) "
						+ "VALUES (?, ?);";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setString(1, comment);
			st.setInt(2, id);
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private List<Item> convertMapToList(Map<Integer, Item> map) {
		List<Item> list = new ArrayList<>();
		for (Map.Entry<Integer, Item> item : map.entrySet()) {
			list.add(item.getValue());
		}
		return list;
	}

	private List<Item> getSetResult(String cond) {
		String query = String.format(
				"SELECT i.id_i, i.name_i, i.description_i, i.create_i, co.id_c, co.comment_c "
						+ "FROM items i LEFT JOIN commentss co "
						+ "ON i.id_i = co.id_i %s;", cond
		);
		List<Item> items = null;
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query);
			 ResultSet rs = st.executeQuery()) {
			Map<Integer, Item> map = new HashMap<>();
			Item item;
			while (rs.next()) {
				int id = rs.getInt("id_i");
				item = map.get(id);
				if (item == null) {
					item = new Item(
							id, rs.getString("name_i"),
							rs.getString("description_i"),
							rs.getDate("create_i")
					);
					map.put(id, item);
				}
				String comment = rs.getString("comment_c");
				if (comment != null) {
					if (item.getComments() == null) {
						item.setComments(new ArrayList<>());
					}
					item.getComments().add(new Comment(rs.getInt("id_c"), comment));
				}
			}
			items = map.isEmpty() ? null : convertMapToList(map);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return items;
	}
}
