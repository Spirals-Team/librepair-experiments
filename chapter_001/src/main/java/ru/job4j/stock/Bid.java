package ru.job4j.stock;


import java.util.Random;

/**
 * Класс реализует функционал биржевой заявки
 */
public class Bid implements Comparable {
    private String id;
    private String book;
    private Type type;
    private Action action;
    private Integer price;
    private Integer volume;

    /**
     * Конструктор с параметрами
     * @param book
     * @param type
     * @param action
     * @param price
     * @param volume
     */
    public Bid( String book,Type type, Action action, int price, int volume ) {
        Random rn = new Random();
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
        this.id = String.valueOf(rn.nextInt(1000000));
    }

    /**
     * Конструктор создающий копию заявки
     * @param bid
     */
    public Bid(Bid bid) {
        this.book = bid.book;
        this.type = bid.type;
        this.action = bid.action;
        this.price = bid.price;
        this.volume = bid.volume;
        this.id = bid.id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getBook() {
        return book;
    }

    public Type getType() {
        return type;
    }

    public Action getAction() {
        return action;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    /**
     * Варианты типов заявки
     */
    enum Type {
        ADD, DELETE
    }

    /**
     * Варианты действий заявки
     */
    enum Action {
        BID, ASK
    }

    @Override
    /**
     * Сортировка заявок происходит по цене в порядке убывания
     */
    public int compareTo(Object o) {
        Bid b = (Bid) o;
        return this.price.compareTo(b.getPrice()) * -1;
    }

    @Override
    /**
     * Сравнение заявок происзодит по всем полям кроме id и type
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (!book.equals(bid.book)) return false;
        if (action != bid.action) return false;
        if (!price.equals(bid.price)) return false;
        return volume.equals(bid.volume);
    }

    @Override
    public int hashCode() {
        int result = book.hashCode();
        result = 31 * result + action.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + volume.hashCode();
        return result;
    }

    @Override
    /**
     * Печать заявки по столбцам в зависимости от ее действий
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        switch (action) {
            case BID:   str.append(String.format("%15s%15s%15s\n"," ", volume, price));
                        break;
            case ASK:   str.append(String.format("%15s%15s%15s\n",price, volume, " "));
                        break;
        }
        return str.toString();
    }
}
