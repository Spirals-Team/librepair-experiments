package ru.job4j.condition;

/**
 * Class Point.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.09.2017
 */
public class Point {
    /**
     * Координата X.
     */
    private int x;
    /**
     * Координата Y.
     */
    private int y;

    /**
     * Конструктор.
     *
     * @param x - координата Х
     * @param y - координата Y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Геттор для координаты Х.
     *
     * @return - возвращает значение Х.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Геттор для координаты Y.
     *
     * @return - возвращает значение Y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Определяем находится ли точка на этой фукнции.
     *
     * @param a - координата X
     * @param b - координата Y
     * @return - возвращает значения true/false
     */
    public boolean is(int a, int b) {
        return (getY() == a * getX() + b);
    }
}
