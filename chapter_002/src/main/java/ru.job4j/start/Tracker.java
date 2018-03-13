package ru.job4j.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Sergey Afonin (mailto: afonin1c@mail.ru)
 * @since 19.10.2017
 * sql added 26.01.2018
 */
public class Tracker {

    /**
     * //sql
     */
    TrackerSQL trackerSQL;

    /**
     * RN is a constant which contains current random value.
     */
    private static final Random RN = new Random();

    /**
     *
     */
    public Tracker() {

    }

    /**
     * //sql
     * @return
     */
    public TrackerSQL getTrackerSQL() {
        return trackerSQL;
    }

    /**
     * //sql
     */
    public void initTracker() {
        trackerSQL = new TrackerSQL();
        trackerSQL.initTrackerSQL();
    }

    /**
     * void add(Item item) {}; добавление заявок - public Item add(Item); 1. Метод public Item add(Item) добавляет заявку, переданную в аргументах в массив заявок this.items.
     *
     * @param item is item for add to array items.
     * @return item.
     */
    public Item add(Item item) {

        item.setId(this.generateId());
        this.trackerSQL.addSql(item);
        return item;
    }

    /**
     * method which generates id for item.
     *
     * @return generated id for item.
     */
    String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
    }

    /**
     * редактирование заявок - public void update(Item); 2. Метод public void update(Item) должен заменить ячейку в массиве this.items.
     * Для этого необходимо найти ячейку в массиве по id (id у Item можно получить с помощью метода getId). Обязательно принимать один параметр Item, а не список полей.
     *
     * @param item is item for update.
     */
    public void update(Item item) {
        trackerSQL.updateSql(item);
    }

    /**
     * удаление заявок - public void delete(Item); Метод public void delete(Item) должен удалить ячейку в массиве this.items. Для этого необходимо найти ячейку в массиве по id.
     * После этого присвоить ей null, либо сместить все значения справа от удаляемого элемента - на одну ячейку влево с помощью System.arrayCopy().
     *
     * @param item is item for delete.
     */
    public void delete(Item item) {
        trackerSQL.deleteSql(item);
    }

    /**
     * получение списка всех заявок - public Item[] findAll(); 4. Метод public Item[] findAll() возвращает копию массива this.items без null элементов;
     *
     * @return all items with not null value
     */
    public Item[] findAll() {
        return trackerSQL.findAllSql();
    }

    /**
     * получение списка по имени - public Item[] findByName(String key); 5. Метод public Item[] findByName(String key) проверяет в цикле все элементы массива this.items,.
     * сравнивая name (используя метод getName класса Item) с аргументом метода String key. Элементы, у которых совпадает name, копирует в результирующий массив и возвращает его;.
     *
     * @param key is a name by which function finds elements from array.
     * @return all items with the same name
     */
//    public Item[] findByName(String key) {
    public ArrayList<Item> findByName(String key) {
        return trackerSQL.findByNameSql(key);
    }

    /**
     * получение заявки по id - public Item findById(String id); Метод public Item findById(String id) проверяет в цикле все элементы массива this.items, сравнивая id с
     * аргументом String id и возвращает найденный Item. Если Item не найден - возвращает null.
     *
     * @param id is id by which function finds elements from array.
     * @return item with the same id.
     */
    protected Item findById(String id) {
        return trackerSQL.findByIdSql(id);
    }

    /**
     * edit item.
     *
     * @param item item for edit.
     */
    void edit(Item item) {
        trackerSQL.editSql(item);
    }

    /**
     * get all items.
     *
     * @return all items
     */
    public Item[] getAll() {
        return trackerSQL.getAllSql();
    }

    /**
     * @param item item.
     * @return item.
     */
    public String showItem(Item item) {
        return "Item id: " + item.getId() + " name: " + item.getName() + " descreption: " + item.getDescription() + " create: " + item.getCreate() + "\n";
    }

    /**
     * @return resultstring.
     */
    public String showAll() {

        Item[] items = getAll();

        String resultString = "";
        for (int index = 0; index < items.length; index++) {
            resultString = resultString + showItem(items[index]);
        }
        return resultString;
    }
}