package ru.aserdyuchenko.tracker.start;

import ru.aserdyuchenko.tracker.models.Item;
import ru.aserdyuchenko.tracker.storage.JdbcStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * В данном классе хранятся все заявки.
 * Возможно добавлять, редактировать и удалять заявки, также
 * возможен вывод списка всех заявок и вывод списка по фильтру.
 */
public class Tracker {
    private JdbcStorage storage = new JdbcStorage();
	private Item[] items = new Item[10];
	private int position = 0;
	private static final Random RN = new Random();

/**
 * Добавление заявок
 */
	public boolean add(Item item) {
	    try {
            item.setId(this.generateId());
            storage.add(item);
            return true;
        } catch (Exception e) {
	        e.printStackTrace();
	        return false;
        }
	}

/**
 * Поиск id by name
 */
	protected String findIdByName(String name) {
		String result = null;
		for (Item item : items)	{
			if (item != null && item.getName().equals(name)) {
				result = item.getId();
			break;
			}
		}
		return result;
	}

/**
 * Generate id for item.
 */
private String generateId() {
		return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
	}

    /**
     * Get all items from DB.
     * @return list with items.
     * @throws SQLException - SQL exception.
     */
	public List<Item> getAll() throws SQLException {
        return storage.get();
	}

/**
 * Редактирование описания
 */
	public Item updateItem(String itemId, Item item){
		int position = 0;
		for (Item itemIndex : items)	{
			for (int index=0; index!=this.position; index++) {
				if (itemIndex != null && itemIndex.getId().equals(itemId)) {
					position = index;
				break;
				}
			}
		}
		this.items[position] = item;
		return this.items[position];
	}

    /**
     * Delete item from DB.
     * @param itemId - id item for delete.
     * @return true if item delete.
     */
	public boolean delete(String itemId) {
	    try {
            storage.delete(itemId);
	        return true;
        } catch (Exception e) {
	        e.printStackTrace();
	        return false;
        }
	}
}
