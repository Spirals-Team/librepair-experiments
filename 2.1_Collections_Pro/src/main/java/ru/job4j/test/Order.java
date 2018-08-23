package ru.job4j.test;

/**
 * Class Order.
 */
public class Order {
    /**
     * enum.
     */
    public enum Type {
        /**
         * SELL sell
         * BUY buy
         */
        SELL, BUY
    }


    protected final String book;
    protected final Type type;
    protected final float price;
    protected int volume;
    protected final int id;

    /**
     * Constructor.
     * @param book book
     * @param type type
     * @param price price
     * @param volume volume
     * @param id id
     */
    public Order(String book, Type type, float price, int volume, int id) {
        this.book = book;
        this.type = type;
        this.price = price;
        this.volume = volume;
        this.id = id;
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

        if (id != order.id) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

