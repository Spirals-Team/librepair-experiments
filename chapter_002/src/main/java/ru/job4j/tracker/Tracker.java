package ru.job4j.tracker;

import java.util.*;

public class Tracker {
    private List<Item> items = new ArrayList<>();
    private static final Random RN = new Random();

    public Item add(Item item) {
        item.setId(this.generateid());
        items.add(item);
        return item;
    }

    private String generateid() {

        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        for (Item item: items) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    public void replace(Item item) {
        for (Item i : items) {
            if (item.getId().equals(i.getId()) && item.getId() != null) {
                items.add(items.indexOf(i), item);
                break;
           }
        }
    }

    public void  delete(String id) {
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                items.remove(items.indexOf(item));
                break;
            }
        }
    }

    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
        if (item != null && item.getName().equals(key)) {
            result.add(item);
            }
        }
        return result;
    }

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