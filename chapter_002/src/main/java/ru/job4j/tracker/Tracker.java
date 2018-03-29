package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Tracker {
    private ArrayList<Item> items = new ArrayList<Item>(100);
    private int position = 0;

    private String generateId() {
        String id = UUID.randomUUID().toString();
        return id;
    }

    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    public void update(Item item) {
        for (int i = 0; i < items.size(); i++) {
                items.set(i, item);
        }
    }

    public void delete(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> findAll() {
        ArrayList<Item> result = new ArrayList<Item>(100);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null)
                result.add(items.get(i));
        }
        return result;
    }

    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<Item>(100);
        for (int i = 0; i < items.size(); i++) {
            if ((items.get(i) != null) && (this.items.get(i).getName().equals(key)))
                result.add(items.get(i));
        }
        return result;
    }

    public Item findById(String id) {
        for (int i = 0; i < items.size(); i++) {
            if ((items.get(i) != null) && (this.items.get(i).getId().equals(id)))
                return items.get(i);
        }
        return null;
    }
}
