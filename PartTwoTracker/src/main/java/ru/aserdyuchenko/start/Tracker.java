package ru.aserdyuchenko.start;

import ru.aserdyuchenko.models.*;
import java.util.*;

/**
 * В данном классе хранятся все заявки.
 * Возможно добавлять, редактировать и удалять заявки, также
 * возможен вывод списка всех заявок и вывод списка по фильтру.
 */

public class Tracker {
	private Item[] items = new Item[10];
	private int position = 0;
	private static final Random RN = new Random();
/**
 * Добавление заявок
 */
	public Item add(Item item) {
		item.setId(this.generateId());
		this.items[position++] = item;
		return item;
	}
/**
 * Поиск по id
 */
	protected Item findById(String id) {
		Item result = null;
		for (Item item : items)	{
			if (item != null && item.getId().equals(id)) {
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
			if (item != null && item.getName().equals(name)) {
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
			if (item != null && item.getName().equals(name)) {
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
	public Item[] getAll() {
		Item[] result = new Item[position];
		for (int index=0; index!=this.position; index++) {
			result[index] = this.items[index];
		}
		return result;
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
 * Удаление заявки
 */
	public Item[] delete(String itemId) {
		//Find position by name
		int position = 0;
		for (Item item : items)	{
			for (int index=0; index!=this.position; index++) {
				if (item != null && item.getId().equals(itemId)) {
					position = index;
				break;
				}
			}
		}
		//Removing Item
		this.items[position]=null;

 		//Если элемент равен нулю, убераем его в конец массива
		for (int externalIndex = items.length - 1; externalIndex >= 0; externalIndex--) {
            for (int internalIndex = 0; internalIndex < externalIndex; internalIndex++) {

				if (items[internalIndex] == null) {
                    Item t = items[internalIndex];
                    items[internalIndex] = items[internalIndex + 1];
                    items[internalIndex + 1] = t;
                }
            }
        }
		//Create cleanArray
		Item[] cleanArray = new Item[10];
        for (int firstIndex = 0; firstIndex <= cleanArray.length-1; firstIndex++){
            for (int secondIndex = firstIndex; secondIndex <= items.length-1; secondIndex++){
                if (items[secondIndex]!=null ){
                    cleanArray[firstIndex]=items[secondIndex];
                    firstIndex++;
                }
            }
        }
		return cleanArray;
	}
}
