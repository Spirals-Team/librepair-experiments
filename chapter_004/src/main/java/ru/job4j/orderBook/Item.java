package ru.job4j.orderBook;

import java.util.UUID;

public class Item {
    private String id;
    private String book;
    private String type;
    private String action;
    private int price;
    private int volume;

    public Item(String book, String type, String action, int price, int volume) {
        this.id = generateId();
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
