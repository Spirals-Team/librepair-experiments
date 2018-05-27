package ru.job4j.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * класс генерации движения.
 */
public class Motion {

    /** Метод генерирут число от 0 до 100 и в зависимости от того, какое вышло число, указывает направление движения.
     * 0-24 - идти налево,
     * 25-49 - идти вправо,
     * 50-74 - идти вниз,
     * 75-99 - идти вверх.
     * @return - возращаем Слово направление движения.
     */
    public String newWay() {
        int vector = (int) (Math.random() * 100);
        if (vector < 25) {
            return "Left";
        } else if (vector < 50) {
            return "Right";
        } else if (vector < 75) {
            return "Down";
        } else {
            return "Up";
        }
    }

    /** Метод генераци позиции (x ,y) на основание направления движения.
     * генерирут только возможный ход. Если направление движение выходит за границы,
     * запрашивает новое направление движения.
     * @param board - доска где ходим и где ставим блокировку если там стоим.
     * @param position - текущая позиция где стоим.
     * @return - возвращаем новую позицию куда можно пойти.
     */
    public Position getWay(ReentrantLock[][] board, Position position) {
        int x = position.getX();
        int y = position.getY();
        boolean goodWay = false;
        do {
            String way = newWay();
            if (way.equals("Left") && x - 1 >= 0) {
                x -= 1;
                goodWay = true;
            } else if (way.equals("Right") && x + 1 < board[y].length) {
                x += 1;
                goodWay = true;
            } else if (way.equals("Up") && y - 1 >= 0) {
                y -= 1;
                goodWay = true;
            } else if (way.equals("Down") && y + 1 < board.length) {
                y += 1;
                goodWay = true;
            }
        } while (!goodWay);
        return new Position(x, y);
    }

    /** Метод генерации новой позиции отличной от предыдущей.
     * @param board - доска где ходим и где ставим блокировку если там стоим.
     * @param position - текущая позиция где стоим.
     * @param lastPosition - позицию куда попытались пойти, но она нам не подошла.
     * @return - возвращаем новую позицию куда можно пойти но отличную от LastPosition.
     */
    public Position getWay(ReentrantLock[][] board, Position position, Position lastPosition) {
        Position newPosition;
        do {
            newPosition = getWay(board, position);
        } while (newPosition.equals(lastPosition));
        return newPosition;
    }
}
