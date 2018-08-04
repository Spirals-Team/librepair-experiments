package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

import static java.lang.Math.abs;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Queen implements IChessMove {
    IChessMove iChessMove;

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int deltaX = abs(dest.getX() - source.getX());
        int deltaY = abs(dest.getY() - source.getY());
        Cell[] way;
        if (deltaX == deltaY) {
            way = new Cell[deltaX];
            iChessMove = new Elephant();
            way = iChessMove.way(source, dest);
        } else {
            if (!((deltaX == 0 & deltaY != 0) | (deltaY == 0 & deltaX != 0))) {
                throw new ImpossibleMoveException("Недопустимый ход");
            }
            way = new Cell[(deltaX == 0) ? deltaY : deltaX];
            iChessMove = new Tower();
            way = iChessMove.way(source, dest);
        }
        return way;
    }
}
