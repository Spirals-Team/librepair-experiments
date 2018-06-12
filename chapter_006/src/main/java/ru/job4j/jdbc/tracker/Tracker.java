package ru.job4j.jdbc.tracker;

import ru.job4j.jdbc.tracker.connect.IConnection;
import ru.job4j.jdbc.tracker.dao.IItemDao;
import ru.job4j.jdbc.tracker.dao.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class Tracker {
	private IItemDao itemDao;

	public Tracker(IItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public boolean add(Item item) {
		return itemDao.add(item);
	}

	Item update(String id, Item item) {
		return itemDao.update(id, item);
	}

	public void delete(String id) {
		itemDao.delete(id);
	}

	public List<Item> findAll() {
		return itemDao.findAll();
	}

	public List<Item> findByName(String key) {
		return itemDao.findByName(key);
	}

	public Item findById(String id) {
		return itemDao.findById(id);
	}
}
