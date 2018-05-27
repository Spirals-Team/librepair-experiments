package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Tracker {

    /**
     * Список для хранение заявок.
     */
    private List<Item> items = new ArrayList<Item>();

    private static final Random RN = new Random();

    private static int index = 1;


    /**
     * Метод добавляет заявку, переданную в аргументах в массив заявок this.items.
     * @param item - заявка.
     */
    public Item add(Item item) {
        item.setId(String.valueOf(this.generateId()));
        this.items.add(item);
        return item;
    };

    /**
     * Метод генерирует уникальный Id.
     * @return - возвращает сгенерированный Id.
     */
    String generateId() {
        return String.valueOf(index++);
    }

    /**
     * Метод заменяет заявку в массиве this.items.
     * @param id - заменяемой заявки.
     * @param item - заявка на которую заменяем.
     */
    public void replace(String id, Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                String key = this.items.get(i).getId();
                this.items.remove(i);
                item.setId(key);
                items.add(item);
                break;
            }
        }
    };

    /**
     *  Метод удалит заявку в массиве this.items.
     * @param id - удаляемой заявки.
     */
    public void delete(String id) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getId().equals(id)) {
                items.remove(i);
                break;
            }
        }
    };

    /**
     * Метод возвращает списко всех заявок;
     * @return - список заявок.
     */
    public List<Item> findAll() {
        return this.items;
    };

    /**
     *  Метод возвращает все заявки по ключевому параметру в имени.
     * @param key - параметр по которому ищем.
     * @return - список заявок содержащих ключ в имени.
     */
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<Item>();
        for (Item iter : this.items) {
            if (iter.getName().equals(key)) {
                list.add(iter);
            }
        }
        return list;
    };

    /**
     * Метод ищет заявку по ID.
     * @param id - ключ по которому ищем заявку.
     * @return - заявку в которой ID соответсвует ключу.
     */
    public Item findById(String id) {
        Item findItem = null;
        for (Item iter : this.items) {
            if (iter.getId().equals(id)) {
                findItem = iter;
                break;
            }
        }
        return findItem;
    };
}
