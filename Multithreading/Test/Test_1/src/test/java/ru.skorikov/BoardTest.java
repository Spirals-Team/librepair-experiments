package ru.skorikov;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 05.03.18
 * @ version: java_kurs_standart
 */
public class BoardTest {
    /**
     * Создадим поле и трех героев.
     */
    @Test
    public void tryCreateBoard() {
        Board board = new Board(6, 3);
        board.startHeroes();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        board.stopHeroes();
    }
}