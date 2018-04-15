package ru.aserdyuchenko.testTask;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class Order {
    private String operation;
    private double price;
    private int volume;
    private int orderID;
    private int countOrders = 0;

    public Order() {
        this.operation = "";
        this.price = 0.0;
        this.volume = 0;
        this.orderID = countOrders++;
    }

    public Order(String operation, double price, int volume, int orderID) {
        this.operation = operation;
        this.price = price;
        this.volume = volume;
        this.orderID = orderID;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
