package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * класс действий с доской.
 */
public class BoardAction {

    /** Метод блокирует клетку доски по указанным координатам.
     * @param board - доска где блокируем.
     * @param position - позиция клетки которую блокируем.
     */
    public void lockBoard(ReentrantLock[][] board, Position position) {
        board[position.getY()][position.getX()].lock();
    }

    /** Метод разблакировки доски.
     * @param board - доска где разблокируем.
     * @param position - позиция клетки которую разблокируем.
     */
    public void unlockBoard(ReentrantLock[][] board, Position position) {
        board[position.getY()][position.getX()].unlock();
    }

    /** Метод проверки на занятость клетки.
     * @param board - доска где проверяем.
     * @param position - клетку которую проверяем.
     * @return - Возвращаяем True если клетка не заблокированна,
     * возвращаем False если клетка заблокированна.
     */
    public boolean isFreeBoard(ReentrantLock[][] board, Position position) {
        return !board[position.getY()][position.getX()].isLocked();
    }

    /** Метод атомарной блокировки клетки.
     * @param board - доска где блокируем.
     * @param position - клетка которую блокируем.
     * @return - резултат блокировки, смогли ли занять клетку.
     */
    public boolean tryLockBoard(ReentrantLock[][] board, Position position) {
        return board[position.getY()][position.getX()].tryLock();
    }

    /** Метод возвращает слючайную свободную позицию.
     * @param board - доска где ищем свободную клетку.
     * @return - координаты свободной клетки.
     */
    public Position generateRandomFreePosition(ReentrantLock[][] board) {
        int x;
        int y;
        do {
            x = (int) (Math.random() * board[0].length);
            y = (int) (Math.random() * board.length);
        } while (board[y][x].isLocked());
        return new Position(x, y);
    }

    /** Метод паузы во времени.
     * @param time - сколько времени в миллесикундах нужно ждать.
     */
    public void sleep(int time) {
        long timeStart = System.currentTimeMillis();
        long timeStop;
        do {
            timeStop = System.currentTimeMillis();
        } while (timeStop - timeStart < time);
    }
}
