package ru.skorikov;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 05.03.18
 * @ version: java_kurs_standart
 */
public class Board {

    /**
     * Игровое поле.
     */
    private final ReentrantLock[][] board;

    /**
     * Количество героев.
     */
    private final Thread[] heroes;

    /**
     * Создатель поля.
     *
     * @param boardSize размер (для простоты - квадратный).
     * @param hero      герои.
     */
    Board(int boardSize, int hero) {
        board = new ReentrantLock[boardSize][boardSize];
        heroes = new Thread[hero];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    /**
     * Получить поле (для героя).
     *
     * @return поле.
     */
    public ReentrantLock[][] getBoard() {
        return board;
    }

    /**
     * Запустить героев по полю.
     */
    public void startHeroes() {
        for (int i = 0; i < heroes.length; i++) {
            String name = "Hero_" + i;
            heroes[i] = new Thread(new Hero(name, this));
            heroes[i].start();
        }
    }

    /**
     * Прибить героев.
     */
    public void stopHeroes() {
        for (Thread heroe : heroes) {
            heroe.interrupt();
        }
    }
}
