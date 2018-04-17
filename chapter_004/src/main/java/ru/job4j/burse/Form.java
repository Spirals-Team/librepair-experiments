package ru.job4j.burse;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Form implements Comparable<Form> {
    private int id;
    private String book;
    private Action action;
    private Type type;
    private double price;
    private int volume;

    public Form(String book, Action action, Type type, double price, int volume) {
        this.book = book;
        this.action = action;
        this.type = type;
        this.price = price;
        this.volume = volume;
        int hash = hashCode();
        this.id = hash < 0 ? -hash : hash;
    }

    public Form(Form form, Type type) {
        this(form.book, form.action, type, form.price, form.volume);
    }

    public String getBook() {
        return book;
    }

    public Action getAction() {
        return action;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public void changeVolume(int volume) {
        this.volume += volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Form form = (Form) o;
        return Double.compare(form.price, price) == 0
                && volume == form.volume
                && action == form.action
                && type == form.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, type, price, volume);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Form{id=%d, book='%s', action=%s, type=%s, price=%.2f, volume=%d}",
                id, book, action.toString(), type.toString(), price, volume);
    }

    @Override
    public int compareTo(Form o) {
        double diff = getPrice() - o.getPrice();
        int result = 0;
        if (diff < 0) {
            result = 1;
        }
        if (diff > 0) {
            result = -1;
        }
        return result;
    }
}
