package ru.job4j.chess;

/**
 * @version $Id$
 * @since 0.1
 */

class Bishop extends Figure {

    private int position = 0;

    Bishop(Cell position) {
        super(position);
    }

    Figure copy(Cell dest) {
        return new Bishop(dest);
    }

    /**
     * Метод проверяет верный ли ход фигуры.
     * Если разница между ординатами и абсцисами равны по модулю метод возвращает true.
     */

    Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        Cell[] result = new Cell[1];
        if ((Math.abs(source.getX() - dest.getX()) == Math.abs(source.getY() - dest.getY()))) {
            result[this.position] = dest;
        } else {
            throw new ImpossibleMoveException("Неверный ход");
        }
        return result;
    }
}