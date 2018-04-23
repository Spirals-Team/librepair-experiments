package ru.aserdyuchenko.tracker.start;

import ru.aserdyuchenko.tracker.models.*;
import java.util.*;

/**
 * В данном классе хранятся все заявки.
 * Возможно добавлять, редактировать и удалять заявки, также
 * возможен вывод списка всех заявок и вывод списка по фильтру.
 */

public class Tracker {
	private List<Item> items = new ArrayList<Item>();
	private static final Random RN = new Random();
/**
 * Добавление заявок
 */
	public Item add(Item item) {
		item.setId(this.generateId());
		this.items.add(item);
		return item;
	}
/**
 * Поиск по id
 */
	protected Item findById(String id) {
		Item result = null;
		for (Item item : items)	{
			if (item.getId().equals(id)) {
				result = item;
				break;
			}
		}
		return result;
	}
/**
 * Find item by name
 */
	protected Item findByName(String name) {
		Item result = null;
		for (Item item : items)	{
			if (item.getName().equals(name)) {
				result = item;
				break;
			}
		}
		return result;
	}
/**
 * Поиск id by name
 */
	protected String findIdByName(String name) {
		String result = null;
		for (Item item : items)	{
			if (item.getName().equals(name)) {
				result = item.getId();
				break;
			}
		}
		return result;
	}
/**
 * Создание id
 */
	String generateId() {
		return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
	}
/**
 * Вывод всех заявок
 */
	public List<Item> getAll() {
		List<Item> result = new ArrayList<Item>();
		result.addAll(this.items);
		return result;
	}
/**
 * Редактирование описания
 */
	public Item updateItem(String itemId, Item item) {
		Item newItem = new Task("-", "-");
		int position = 0;
		for (Item itemIndex : items)	{
			if (itemIndex.getId().equals(itemId)) {
				this.items.set(position, item);
				newItem = this.items.get(position);
				break;
			} else {
				position++;
			}
		}
		return newItem;
	}
/**
 * Удаление заявки
 */
	public List<Item> delete(String itemId) {
		Iterator<Item> iter = items.iterator();
		while (iter.hasNext()) {
			Item item = iter.next();
			if (item.getId().equals(itemId)) {
				iter.remove();
			}
		}
		
		List<Item> cleanArray = new ArrayList<Item>();
		cleanArray.addAll(this.items);
		return cleanArray;
	}
}
