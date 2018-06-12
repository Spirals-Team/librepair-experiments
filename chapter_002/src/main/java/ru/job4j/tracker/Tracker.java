package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private List<Item> items = new ArrayList<>();

    public String indexOf(Item item) {
        return item.getId();
    }

    public Item add(Item item) {
        this.items.add(item);
        return item;
    }

    public void replace(String id, Item item) {
        for (Item elem : this.items) {
            if (id.equals(indexOf(elem))) {
                elem.setName(item.getName());
                elem.setDescription(item.getDescription());
                elem.setCreate(item.getCreate());
                break;
            }
        }
    }

    public void delete(String id) {
        int i = 0;
        for (Item elem : items) {
            if (elem.getId().equals(id)) {
                break;
            }
            i++;
        }
        items.remove(i);
    }

    public Item[] findAll() {
        int i = 0;
        Item[] cur = new Item[items.size()];
        for (Item elem : items) {
            cur[i++] = elem;
        }
        return cur;
    }

    public Item[] findByName(String key) {
        Item[] cur = new Item[this.items.size()];
        int i = 0;
        for (Item elem : this.items) {
            if (elem.getName().equals(key)) {
                cur[i++] = elem;
            }
        }
        return Arrays.copyOf(cur, i);
    }

    public Item findById(String id) {
        Item cur = null;
        for (Item elem : this.items) {
            if (elem.getId().equals(id)) {
                cur = elem;
            }
        }
        return cur;
    }
}
