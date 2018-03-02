package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * tracker.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /*** List of items.*/
    private List<Item> items = new ArrayList<>();
    /*** Random.*/
    private static final Random RN = new Random();
    /**
     * Generate id.
     * @return new id
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
    /**
     * Add item.
     * @param item - item
     * @return added item
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }
    /**
     * Update item.
     * @param item - item
     */
    public void update(Item item) {
        int index = 0;
        for (Item val : this.items) {
            if (val.getId().equals(item.getId())) {
                this.items.set(index, item);
                break;
            }
            index++;
        }
    }
    /**
     * Delete item.
     * @param item - item
     */
    public void delete(Item item) {
        this.items.remove(item);
    }
    /**
     * Find all items.
     * @return Items array without nulls
     */
    public List<Item> findAll() {
        //return this.items.toArray(new Item[this.items.size()]);
        return this.items;
    }
    /**
     * Find items by name.
     * @param name - name
     * @return items array
     */
    public List<Item> findByName(String name) {
        List<Item> list = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(name)) {
                list.add(item);
            }
        }
        return list;
    }
    /**
     * Find item by id.
     * @param id - id
     * @return item
     */
    public Item findById(String id) {
        Item item = null;
        for (Item val : this.items) {
            if (val.getId().equals(id)) {
                item = val;
            }
        }
        return item;
    }
}
