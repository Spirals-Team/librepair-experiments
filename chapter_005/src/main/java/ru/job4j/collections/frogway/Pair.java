package ru.job4j.collections.frogway;

/**
 * Ячейка в массиве.
 * @author Hincu Andrei (andreih1981@gmail.com) by 01.11.17;
 * @version $Id$
 * @since 0.1
 */
public class Pair {
    /**
     * Координата х в массиве.
     */
    private int x;
    /**
     * координата у в массиве.
     */
    private int y;
    /**
     * Колличество ходов до данной точки.
     */
    private int value;

    public Pair(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    /**
     * Ячейка откуда пришли в данную ячейку.
     */
    private Pair prev;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Pair getPrev() {
        return prev;
    }

    public Pair(int x, int y, int value, Pair prev) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair pair = (Pair) o;

        if (x != pair.x) {
            return false;
        }
        if (y != pair.y) {
            return false;
        }
        if (value != pair.value) {
            return false;
        }
        return prev != null ? prev.equals(pair.prev) : pair.prev == null;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + value;
        result = 31 * result + (prev != null ? prev.hashCode() : 0);
        return result;
    }

    public void setPrev(Pair prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Ячейка - ("
                + (y + 1)
                + ","
                + (x + 1)
                + ") ход: "
                + (value - 1);
    }
}
