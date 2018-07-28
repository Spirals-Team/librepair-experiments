package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

import static java.lang.Math.abs;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class PawnUp implements IChessMove {
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int deltaX = dest.getX() - source.getX();
        int deltaY = dest.getY() - source.getY();
        Cell[] way = new Cell[deltaY];
        if (!(deltaY == 1 && deltaX == 0)) {
            throw new ImpossibleMoveException("Недопустимый ход");
        }
        way[0] = new Cell(source.getX(), source.getY() + deltaY);
        return way;
    }
}
