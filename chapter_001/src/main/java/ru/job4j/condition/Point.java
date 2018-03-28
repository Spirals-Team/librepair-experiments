package ru.job4j.condition;

/**
 * Point class.
 *
 * * @author Artem Lipatov
 * */

public class Point {

    /**
     * x.
     * * @author Artem Lipatov
     * */

    private int x;
    /**
     * y.
     * * @author Artem Lipatov
     * */
    private int y;

    /**
     * Constructor.
     *
     * * @param x x
     * * @param y y
     * * @author Artem Lipatov
     */

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for X.
     *
     * * @author Artem Lipatov
     * * @return x
     */

    public int getX() {
        return this.x;
    }

    /**
     * Getter for Y.
     *
     * * @author Artem Lipatov
     * * @return y
     */

    public int getY() {
        return this.y;
    }

    /**
     * Method for testing if point is on the line.
     *
     * * @author Artem Lipatov
     * * @param a a
     * * @param b b
     * * @return if true if x = y
     */

    public boolean is(int a, int b) {
        int y = this.y;
        int x = this.x;
        x = x * a + b;
        return (x == y) ? true : false;
    }
}
