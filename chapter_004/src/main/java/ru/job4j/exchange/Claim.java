package ru.job4j.exchange;

import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Claim {
    private static int count = 0;
    private int id;
    private String book;
    private Type type;
    private Action action;
    private float price;
    private int volume;

    public Claim(String book, Type type, Action action, float price, int volume) {
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
        this.id = count++;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public String getBook() {
        return this.book;
    }

    public Type getType() {
        return this.type;
    }

    public Action getAction() {
        return this.action;
    }

    public float getPrice() {
        return this.price;
    }

    public int getVolume() {
        return this.volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Claim claim = (Claim) o;
        return Float.compare(claim.price, price) == 0
                && volume == claim.volume
                && Objects.equals(book, claim.book)
                && action == claim.action;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, book, type, action, price, volume);
    }

    @Override
    public String toString() {
        String action;
        if (this.action == Action.ASK) {
            action = "продажа";
        } else {
            action = "покупка";
        }
        return String.format("#%d %s %s по цене: %.2f - %d шт.",
                this.id, this.book, action, this.price, this.volume);
    }

    public enum Type {
        ADD,
        DEL;
    }

    public enum Action {
        BID,
        ASK;
    }
}
