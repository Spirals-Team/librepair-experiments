package ru.job4j.condition;

/**
 * Check is point on line.
 *
 * @author Afonin Sergey (afonin1c@mail.ru)
 * @version 01
 * @since 06.10.2017
 */
public class Point {

    /**
     * @class verible x point position on line x.
     */
    private int x;
    /**
     * @class verible y point position on line y.
     */
    private int y;

    /**
     * Method Point.
     * @param x point position on line x.
     * @param y point position on line y.
     */
    public  Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method getX.
     * @return point position on line x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Method getY.
     * @return point position on line y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Method is.
     * @param a is a constant in expression y(x) = a * x + b.
     * @param b is a constant in expression y(x) = a * x + b.
     * @return is the point (x,y) find's on line y(x) = a * x + b.
     */
    public boolean is(int a, int b) {

        /*
        if (this.y == this.x*a+b) {
            return true;
        } else {
            return false;
        }
        */
        return this.y == this.x * a + b;
    }
}