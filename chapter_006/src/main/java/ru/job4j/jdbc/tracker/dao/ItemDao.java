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
	public boolean add(Item item) {
		boolean result = true;
		try (Connection conn = typeConn.connect()) {
			PreparedStatement st = conn.prepareStatement(
					"INSERT INTO ITEMS (name_i, description_i, create_i)"
							+ " VALUES (?, ?, ?);"
			);
			st.setString(1, item.getName());
			st.setString(2, item.getDescription());
			st.setDate(3, item.getCreate());
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			result = false;
		}
		return result;
	}

	@Override
	public Item update(String id, Item item) {
		return null;
	}

	@Override
	public void delete(String id) {

	}

	@Override
	public List<Item> findAll() {
		List<Item> items = null;
		try (Connection conn = typeConn.connect()) {
			String query =
					"SELECT i.id_i, i.name_i, i.description_i, i.create_i, co.comment_c "
							+ "FROM items i LEFT JOIN commentss co "
							+ "ON i.id_i = co.id_i;";
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			Map<Integer, Item> map = new HashMap<>();
			Item item;
			while (rs.next()) {
				int id = rs.getInt("id_i");
				item = map.get(id);
				if (item == null) {
					item = new Item();
					item.setId(id);
					item.setName(rs.getString("name_i"));
					item.setDescription(rs.getString("description_i"));
					item.setCreate(rs.getDate("create_i"));
					map.put(id, item);
				}
				String comment = rs.getString("comment_c");
				if (comment != null) {
					if (item.getComments() == null) {
						item.setComments(new ArrayList<>());
					}
					item.getComments().add(comment);
				}
			}
			items = map.isEmpty() ? null : convertMapToList(map);
			rs.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return items;
	}

	@Override
	public List<Item> findByName(String key) {
		return null;
	}

	@Override
	public Item findById(String id) {
		return null;
	}

	private List<Item> convertMapToList(Map<Integer, Item> map) {
		List<Item> list = new ArrayList<>();
		for (Map.Entry<Integer, Item> item : map.entrySet()) {
			list.add(item.getValue());
		}
		return list;
	}
}
