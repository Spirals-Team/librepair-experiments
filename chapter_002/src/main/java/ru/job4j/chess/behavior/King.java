package ru.job4j.chess.behavior;

import ru.job4j.chess.board.Cell;
import ru.job4j.chess.exeption.ImpossibleMoveException;

import static java.lang.Math.abs;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class King implements IChessMove {
    IChessMove iChessMove;
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int deltaX = abs(dest.getX() - source.getX());
        int deltaY = abs(dest.getY() - source.getY());
        Cell[] way;
        if (!((deltaX == 1 & (deltaY == 0 | deltaY == 1)) | (deltaY == 1 & (deltaX == 0 | deltaX == 1)))) {
            throw new ImpossibleMoveException("Недопустимый ход");
        }
        iChessMove = new Queen();
        way = iChessMove.way(source, dest);
        return way;
    }
}
