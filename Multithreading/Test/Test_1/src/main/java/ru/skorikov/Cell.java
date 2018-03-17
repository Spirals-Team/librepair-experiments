package ru.skorikov;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 17.03.18
 * @ version: java_kurs_standart
 * Класс ячейка доски.
 */
public class Cell extends ReentrantLock {
    /**
     * Координаты горизонтали.
     */
    private final int x;
    /**
     * Координаты вертикали.
     */
    private final int y;

    /**
     * Создатель доски.
     *
     * @param x координата по горизонтали.
     * @param y координата по вертикали.
     */
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получить координату x.
     *
     * @return x.
     */
    public int getX() {
        return x;
    }

    /**
     * Получить координату y.
     *
     * @return y.
     */
    public int getY() {
        return y;
    }

}
