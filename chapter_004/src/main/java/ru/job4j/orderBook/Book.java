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
        tryToCombine();

        for (Map.Entry<String, Item> entry : map.entrySet()) {
            Item value = entry.getValue();
            String out = String.format("Action: %s amount: %d price: %d", value.getAction(), value.getAmount(), value.getPrice());
            System.out.println(out);
        }
    }

    private void tryToCombine() {
        boolean stop = true;
        String key = "";
        HashMap<String, Item> map = new HashMap<>();
        while (stop) {
            stop = hasSameAmount();
            finish:
            for (Map.Entry<String, Item> entryCurrent : itemsToBuy.entrySet()) {
                Item valueCurrent = entryCurrent.getValue();
                for (Map.Entry<String, Item> entryNext : itemsToBuy.entrySet()) {
                    Item valueNext = entryNext.getValue();
                    if (valueCurrent.getPrice() == valueNext.getPrice() && !(valueCurrent.getId().equals(valueNext.getId()))) {
                        int amount = valueNext.getAmount();
                        key = valueNext.getId();
                        valueCurrent.setAmount(valueCurrent.getAmount() + amount);
                        if (itemsToBuy.entrySet().iterator().next().equals(null)) {
                            stop = true;
                        }
                        break finish;
                    }
                }
            }
            itemsToBuy.remove(key);
        }
    }

    private boolean hasSameAmount() {
        boolean stop = false;
        HashMap<String, Item> map = new HashMap<>();
        finish:
        for (Map.Entry<String, Item> entryCurrent : itemsToBuy.entrySet()) {
            Item valueCurrent = entryCurrent.getValue();
            for (Map.Entry<String, Item> entryNext : itemsToBuy.entrySet()) {
                Item valueNext = entryNext.getValue();
                if (valueCurrent.getPrice() == valueNext.getPrice() && !(valueCurrent.getId().equals(valueNext.getId()))) {
                    stop = true;
                    break finish;

                }
            }
        }
        return stop;
    }
}
