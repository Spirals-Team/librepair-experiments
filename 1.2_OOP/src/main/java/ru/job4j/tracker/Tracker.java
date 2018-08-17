package ru.job4j.tracker;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
*Класс tracker.
*@author ifedorenko
*@since 24.08.2017
*@version 1
*/
public class Tracker {
	/**
	*@param для получения рандомного числа
	*/
	private static final Random RN = new Random();
	/**
	*@param массив на 100 элементов, для хранения заявок
	*/
	private List<Item> items = new ArrayList<>();
	/**
	*Метод добавляет новую заявку в систему.
	*@param item заявка
	*@return item
	*/
	public Item add(Item item) {
		item.setId(this.generateId());
		items.add(item);
		return item;
	}
	/**
	*Метод обновляет заявку в системе.
	*@param item заявка для обновления
	*/
	public void update(Item item) {
		String id = item.getId();
		for (int i = 0; i < this.items.size(); i++) {
			if (this.items.get(i).getId().equals(id)) {
				this.items.set(i, item);
				break;
			}
		}
	}
	/**
	*Метод удаляет заявку из системы.
	*@param item заявка, которую необходимо удалить
	*/
	public void delete(Item item) {
		String id = item.getId(); //получаем id удаляемого объекта
		int length = this.items.size();
		for (int i = 0; i < length; i++) {
				if (this.items.get(i).getId().equals(id)) { //ищем по id элемент, который хотим удалить.
					this.items.remove(i);
					break;
				}
		}
	}
	/**
	*Getter.
	*@return массив из заведенных в систему заявок
	*/
	public List<Item> getItems() {
		return items;
	}

	/**
	*@param key Имя заявки
	*@return Массив из элементов, с заданным именем
	*/
	public List<Item> findByName(String key) {
		List<Item> result = new ArrayList<>();
		for (Item item : this.items) {
			if (item.getName().equals(key)) {
				result.add(item);
			}
		}
		return result; //возвращаем массив без элементов с null
	}
	/**
	*Метод генерирует id для заявки.
	*@return id
	*/
	private String generateId() {
		return String.valueOf(System.currentTimeMillis() + RN.nextInt());
	}
	/**
	*Метод ищет заявку по заданному id.
	*@param id для поиска заявки
	*@return заявку с заданным id
	*/
	public Item findById(String id) {
		Item result = null;
		for (Item item : items) {
			if (item != null && item.getId().equals(id)) {
				result = item;
				break;
			}
		}
		return result;
	}
}