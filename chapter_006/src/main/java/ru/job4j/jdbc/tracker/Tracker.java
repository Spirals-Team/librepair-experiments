package ru.job4j.jdbc.tracker;

import ru.job4j.jdbc.tracker.dao.IItemDao;
import ru.job4j.jdbc.tracker.dao.Item;

import java.util.List;

/**
 * @author Yury Matskevich
 */
public class Tracker {
	private IItemDao itemDao;

	public Tracker(IItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public Item add(Item item) {
		return itemDao.add(item);
	}

	Item update(int id, Item item) {
		return itemDao.update(id, item);
	}

	public void delete(int id) {
		itemDao.delete(id);
	}

	public List<Item> findAll() {
		return itemDao.findAll();
	}

	public List<Item> findByName(String key) {
		return itemDao.findByName(key);
	}

	public Item findById(int id) {
		return itemDao.findById(id);
	}

	public void writeComment(int id, String comment) {
		itemDao.writeComment(id, comment);
	}
}
