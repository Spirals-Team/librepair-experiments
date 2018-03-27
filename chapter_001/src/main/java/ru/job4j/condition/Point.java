package ru.job4j.condition;
/**
 * Point.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Point {
    /**
     * Координата x точки.
     */
    private int x;
    /**
     * Координата y точки.
     */
    private int y;

    /**
     * Конструктор класса.
     * @param x - координата.
     * @param y - координата y.
     */
    public  Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Геттер х.
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Геттер y.
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Метод проверяет принадлежит ли точка прямой.
     * @param a - угловой коэффициент.
     * @param b - свободный член.
     * @return true or false.
     */
    public boolean is(int a, int b) {
        return this.y == a * this.x + b;
    }
}
