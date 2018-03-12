package ru.job4j.tracker.start;

import ru.job4j.tracker.models.Item;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class Tracker.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 29.09.2017
 */
public class Tracker {
    /**
     * Лист заявок.
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Статическая ссылка на объект рандом.
     */
    private static final Random RN = new Random();

    /**
     * Метод add добавляет новую заявку.
     *
     * @param item входящий параметр Item
     * @return вернем заявку
     */
    public Item add(Item item) {
        item.setId(generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод findById производит поиск задачи по id.
     *
     * @param id принимаем входящий параметр id
     * @return вернем найденную заявку по id
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

    /**
     * Метод generateId генерирует уникальный id для задачи.
     *
     * @return вернем уникальный id в виде String
     */
    String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
    }

    /**
     * Метод getAll вернет массив всех задач.
     *
     * @return вернет массив задач
     */
    public ArrayList<Item> getAll() {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : items) {
            result.add(item);
        }
        return result;
    }

    /**
     * Метод update обновляет задачу.
     *
     * @param item входящий параметр объект задачи
     */
    public void update(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (item != null && items.get(i).getId().equals(item.getId())) {
                items.set(i, item);
                break;
            }
        }
    }

    /**
     * Метод delete удаляет задачу.
     *
     * @param item входящий параметр в виде объекта Item
     */
    public void delete(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (item != null && items.get(i).getId().equals(item.getId())) {
                items.remove(i);
                break;
            }
        }
    }

    /**
     * Метод findByName ищет в массиве все задачи по имени.
     *
     * @param key принимаем имя задачи в виде String1
     * @return вернем массив найденных задач
     */
    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item != null && item.getName().equals(key)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Массив findAll ищет все задачи.
     *
     * @return вернем массив задач без пустых ссылок
     */
    public ArrayList<Item> findAll() {
        ArrayList<Item> itemsNew = new ArrayList<>();
        for (Item item : items) {
            if (item != null) {
                itemsNew.add(item);
            }
        }

        return itemsNew;
    }
}
