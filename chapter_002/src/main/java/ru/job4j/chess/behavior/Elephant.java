package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

import static java.lang.Math.abs;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Elephant implements IChessMove {
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int deltaX = abs(dest.getX() - source.getX());
        int deltaY = abs(dest.getY() - source.getY());
        int signX = (dest.getX() - source.getX() < 0) ? -1 : 1;
        int signY = (dest.getY() - source.getY() < 0) ? -1 : 1;
        Cell[] way = new Cell[deltaX];
        if (deltaX != deltaY) {
            throw new ImpossibleMoveException("Недопустимый ход");
        }
        for (int i = 0, mull = 1; i < way.length; i++, mull++) {
            way[i] = new Cell(source.getX() + signX * mull, source.getY() + signY * mull);
        }
        return way;
    }
}
