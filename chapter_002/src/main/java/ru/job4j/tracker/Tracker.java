package ru.job4j.tracker;

import  java.util.*;
import java.util.Arrays;
import ru.job4j.models.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private final ArrayList<Item> items = new ArrayList<>();


	/**
     * Генератор случайных чисел.
     */
	private static final Random RN = new Random();
	private int position = 0;
    /**
     * Метод реализующий добавление заявки в хранилище.
     * @param item новая заявка.
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }
	
	/**
     * Метод реализаущий поиск заявки в хранилище по заданному id.
     * @param id искомой заявки.
     */
	public Item findById(String id) {
		Item result = null;
		for (Item item : items) {
			if ((item != null) && (item.getId().equals(id))) {
				result = item;
				break;
			}
		}
		return result;
	}
	
	/**
     * Метод реализаущий поиск всех непустых ячеек в хранилище.
     * @return возвращает копию массива this.items без null элементов.
     */
	public ArrayList<Item> findAll() {
		ArrayList<Item> result = new ArrayList<>();
			for (Item item : items)	{
				if (item != null) {
					result.add(item);
				}
			}
		return result;
	}
	
	/**
     * Метод реализаущий замену ячейки с id в хранилище на ячейку item .
     * @param id искомой заявки, item заменяющая заявка.
     */
	public void replace(String idrpl, Item itemrpl) {
		for (int index = 0; index < items.size(); index++) {
			if ((items.get(index) != null) && (items.get(index).getId().equals(idrpl))) {
				items.set(index, itemrpl);
			}
		}
	}
	
	/**
     * Метод реализаущий удаление ячейки с заданным id .
     * @param id удаляемой заявки.
     */
	public void delete(String id) {
		for (int i = 0; i < items.size(); i++) {
			if ((items.get(i) != null) && (items.get(i).getId().equals(id))) {
				items.remove(items.get(i));
			}
		}
	}
	
	/**
     * Метод реализаущий реализующий поиск в хранилище ячейки по заданному имени.
     * @param key имя искомой ячейки.
     */
	public Item findByName(String key) {
		Item result = null;
		for (Item item : items) {
			if (item != null && item.getName().equals(key)) {
				result = item;
			}
		}
		return result;
		
	}
	
    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
    }
	
}