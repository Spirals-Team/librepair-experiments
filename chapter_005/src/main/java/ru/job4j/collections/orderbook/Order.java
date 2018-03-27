package ru.job4j.collections.orderbook;

/**
 * Ордер.
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Order implements Comparable<Order> {
    private String book;
    private String operation;
    private float price;
    private int volume;
    private int id;

    public Order() {
    }

    public Order(String operation, float price, int volume) {
        this.operation = operation;
        this.price = price;
        this.volume = volume;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String s;
        if (this.operation.equals("SELL")) {
            s = price + " @ " + volume;
        } else {
            s = volume + " @ " + price;
        }
        return s;
    }

    /**
     * Метод сравнивает объекты по типу операции если они равны тогда по цене.
     * @param order ордер с которым сравнивается.
     * @return больше или меньше для позиции в треесете.
     */
    @Override
    public int compareTo(Order order) {
        int temp = order.operation.compareTo(this.operation);
        if (temp == 0) {
            if (this.operation.equals("BUY")) {
                if ((order.getPrice() - this.getPrice()) > 0) {
                    temp = 1;
                } else {
                    temp = -1;
                }
            } else {
                if ((order.getPrice() - this.getPrice()) < 0) {
                    temp = 1;
                } else {
                    temp = -1;
                }
            }
        }
        return temp;
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

        if (Float.compare(order.price, price) != 0) {
            return false;
        }
        if (volume != order.volume) {
            return false;
        }
        if (id != order.id) {
            return false;
        }
        if (book != null ? !book.equals(order.book) : order.book != null) {
            return false;
        }
        return operation != null ? operation.equals(order.operation) : order.operation == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + volume;
        result = 31 * result + id;
        return result;
    }
}
