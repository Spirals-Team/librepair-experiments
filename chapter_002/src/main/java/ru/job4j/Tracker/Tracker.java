package ru.job4j.tracker;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

import java.util.*;

public class Tracker {

    private ArrayList<Item> items = new ArrayList<>();

    private int position = 0;

    private static final Random RN = new Random();

    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(position++, item);
        return item;
    }

    public void replace(String id, Item item) {
        for (Item i : items) {
            if (i.getId().equals(id)) {
                item.setId(i.getId());
                items.remove(i);
                items.add(item);
                break;
            }
        }
    }

    public void delete(String id) {
        for (Item index : items) {
            if (index.getId().equals(id)) {
                items.remove(index);
                break;
            }
        }
    }

    public ArrayList<Item> findAll() {
        return this.items;
    }

    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (!items.isEmpty() && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
}