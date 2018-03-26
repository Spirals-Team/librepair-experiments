package ru.job4j.orderBook;

import java.util.HashMap;
import java.util.Map;

public class Book {
    private String name;
    private HashMap<String, Item> itemsToBuy;
    private HashMap<String, Item> itemsToSell;

    public Book(String name) {
        this.name = name;
        this.itemsToBuy = new HashMap<>();
        this.itemsToSell = new HashMap<>();
    }

    public void addItem(Item item) {
        if (item.getAction().toLowerCase() == "buy")
            itemsToBuy.put(item.getId(), item);
        if (item.getAction().toLowerCase() == "sell")
            itemsToSell.put(item.getId(), item);
    }

    public void removeItem(String id) {
        itemsToBuy.remove(id);
        itemsToSell.remove(id);
    }

    public void print() {
        init(itemsToBuy);
        init(itemsToSell);
    }

    private void init(HashMap<String, Item> map) {
        for (Map.Entry<String, Item> entry : map.entrySet()) {
            Item value = entry.getValue();
            String out = String.format("To buy: amount: %d price: %d",value.getVolume(), value.getPrice());
            System.out.println(out);
        }
    }
}
