package ru.job4j.chess;

/**
 * Класс, определяющий игровую клетку
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class Cell {
    private int x;
    private int y;

    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получить координату Х клетки
     * @return значение Ч
     */
    public int getX() {
        return this.x;
    }

    /**
     * Получить координату Y клетки
     * @return значение Y
     */
    public int getY() {
        return this.y;
    }
}
