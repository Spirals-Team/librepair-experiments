package ru.job4j.orderbook;

public class Order {
    private int id;
    private String book;
    private String operation;
    private float price;
    private int volume;

    public Order(String book, String operation, float price, int volume, int id) {
        this.book = book;
        this.operation = operation;
        this.price = price;
        this.volume = volume;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getOperation() {
        return operation;
    }

    public float getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }
}
