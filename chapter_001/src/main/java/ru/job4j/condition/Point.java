package ru.job4j.condition;

/**
 * Point.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Point {
    /**
     * Coordinate x.
     */
    private int x;
    /**
     * Coordinate y.
     */
    private int y;
    /**
     * Constructor.
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * getX.
     * @return x
     */
    public int getX() {
        return this.x;
    }
    /**
     * getY.
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Function y(x) = a * x + b.
     * @param a - first value
     * @param b - second value
     * @return true or false
     */
    public boolean is(int a, int b) {
        return this.y == this.x * a + b;
    }
}
