package ru.job4j.interview.orderbook;

import java.util.Objects;

/**
 * Order.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Order {

    private int orderId;
    private Operation operation;
    private int volume;
    private double price;

    public Order() {
    }

    public Order(int orderId, Operation operation, int volume, double price) {
        this.orderId = orderId;
        this.operation = operation;
        this.volume = volume;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{"
            + "orderId=" + orderId
            + ", operation=" + operation
            + ", volume=" + volume
            + ", price=" + price
            + "}";
    }

    public String toStringSimple() {
        return volume + "@" + price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
