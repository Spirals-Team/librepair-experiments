package ru.job4j.orderBook;

import java.util.UUID;

public class Item {
    private String id;
    private String book;
    private String type;
    private String action;
    private int price;
    private int amount;

    public Item(String book, String type, String action, int price, int amount) {
        this.id = generateId();
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
